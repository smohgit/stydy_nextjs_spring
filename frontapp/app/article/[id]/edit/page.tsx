'use client'

import React, {useEffect, useState} from 'react';
import {useParams} from "next/navigation";

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
    fetch(`http://localhost:8090/api/v1/articles/${params.id}`)
      .then(result => result.json())
      .then(result => setArticle(result.data.articles))
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const response = await fetch(`http://localhost:8090/api/v1/articles/${params.id}`, {
      method: "PATCH",
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(article)
    })

    if (response.ok) {
      alert('success update')
    }else{
      alert('update fail')
    }
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