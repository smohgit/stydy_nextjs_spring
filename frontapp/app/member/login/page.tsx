'use client'

import React, {useEffect, useState} from 'react';
import {useParams} from "next/navigation";
import api from "@/app/utils/api";

export default function Login() {
  const [user, setUser] = useState({username: '', password: ''});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUser({...user, [name]: value});
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await api.post("/members/login", user)
  };

  const handleLogout = async () => {
    await api.post("/members/logout")
  };
  return (
    <>
      <h4>로그인</h4>
      <form onSubmit={handleSubmit}>
        <input type="text" name="username" onChange={handleChange} placeholder="아이디"></input>
        <input type="password" name="password" onChange={handleChange} placeholder="비밀번호"></input>
        <input type="submit" value="로그인"/>
        <br/>
        <button onClick={handleLogout}>로그아웃</button>
      </form>

    </>
  );
};