'use client'

import React, {useEffect, useState} from 'react';
import {useParams} from "next/navigation";

export default function Login() {
  const [user, setUser] = useState({username: '', password: ''});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUser({...user, [name]: value});
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const response = await fetch(`http://localhost:8090/api/v1/members/login`, {
      method: "POST",
      credentials: "include", // 인증 정보 함께 보내는 경우 정의 필요
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(user)
    })

    if (response.ok) {
      alert('success')
    }else{
      alert('fail')
    }
  };

  const handleLogout = async () => {
    const response = await fetch(`http://localhost:8090/api/v1/members/logout`, {
      method: "POST",
      credentials: "include", // 인증 정보 함께 보내는 경우 정의 필요
    })

    if (response.ok) {
      alert('success')
    }else{
      alert('fail')
    }
  };
  return (
    <>
      <h4>로그인</h4>
      <form onSubmit={handleSubmit}>
        <input type="text"  name="username" onChange={handleChange} placeholder="아이디"></input>
        <input type="password"  name="password" onChange={handleChange} placeholder="비밀번호"></input>
        <input type="submit" value="로그인"/>
        <button onClick={handleLogout}>로그아웃</button>
      </form>

    </>
  );
};