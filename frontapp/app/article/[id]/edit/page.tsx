'use client'

import React, {useEffect, useState} from 'react';
import {useParams} from "next/navigation";
import api from "@/app/utils/api";

export default function ArticleEdit() {
  const params = useParams();
  const [article, setArticle] = useState({subject: '', content: ''});

  useEffect(() => {
    fetchArticles()
  }, [])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setArticle({...article, [name]: value});
  };

  const fetchArticles = () => {
      api.get(`/articles/${params.id}`)
      .then(response => setArticle(response.data.data.articles))
      .catch (err => {
        console.log(err)
      })
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await api.patch(`/articles/${params.id}`, article)
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
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
  );
};