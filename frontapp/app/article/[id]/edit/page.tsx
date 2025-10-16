'use client'

import React, {useEffect, useState} from 'react';
import {useParams, useRouter} from "next/navigation";
import api from "@/app/utils/api";

export default function ArticleEdit() {
  const params = useParams();
  const router = useRouter();
  const [article, setArticle] = useState({subject: '', content: ''});
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    fetchArticles()
  }, [])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setArticle({...article, [name]: value});
  };

  const fetchArticles = async () => {
    await api.get("/members/me")
      .then(response => console.log(response))
      .catch( err => {
        console.log(err)
        alert("로그인 후 이용해주세요")
        router.push("/member/login")
      })
    
    api.get(`/articles/${params.id}`)
      .then(response => {
        setArticle(response.data.data.articles)
        setIsLoading(true)
      })
      .catch (err => {
        console.log(err)
      })

  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await api.patch(`/articles/${params.id}`, article)
      .then(function (response) {
        console.log(response);
        router.push('/article');
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <>
      {
        isLoading &&
          <>
            <h4>게시물 수정</h4>
            <form onSubmit={handleSubmit}>
              <label htmlFor="title">
                제목 :
                <input type="text" name="subject" value={article.subject} onChange={handleChange}/>
              </label>
              <br/>
              <label htmlFor="subject">
                내용 :
                <input type="text" name="content" value={article.content} onChange={handleChange}/>
              </label>

              <input type={"submit"} value={"수정"}/>
            </form>
          </>
      }

    </>
  );
};