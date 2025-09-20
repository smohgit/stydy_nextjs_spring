'use client'

import {useParams} from "next/navigation";

export default function ArticleDetail() {
  const params = useParams();
  console.log('[sm5 debug][default.ArticleDetail]  : ', params );



  return (
    <div>게시판 상세 {params.id}번</div>
  );
}