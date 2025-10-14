'use client'

import {useParams} from "next/navigation";
import {useEffect, useState} from "react";
import Link from "next/link";
import api from "@/app/utils/api";

export default function ArticleDetail() {
  const params = useParams();
  const [article, setArticle] = useState({});

  useEffect(() => {
    api.get(`/articles/${params.id}`)
      .then(response => setArticle(response.data.data.articles))
      .catch (err => {
        console.log(err)
      })
  }, [])

  // const params = useParams();
  // console.log('[sm5 debug][default.ArticleDetail]  : ', params );



  return (
    <>
      <div>게시판 상세 {params.id}번</div>
      <div>{article.createdDate}</div>
      <div>{article.modifiedDate}</div>
      <div>{article.subject}</div>
      <div>{article.content}</div>
      <Link href={`/article/${params.id}/edit`}>수정</Link>
    </>
  );
}