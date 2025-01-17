package com.example.books.application

import cats.effect.IO
import com.example.books.domain.author.{Author, AuthorRepository}
import com.example.books.domain.common.Id
import com.example.shared.domain.page.{PageRequest, PageResponse}

final class AuthorService(repo: AuthorRepository) extends CommonService {

  private def upsert(author: Author) = for {
    _ <- validateRequest(author)
    _ <- repo.upsert(author)
  } yield ()

  def create(author: Author): IO[Unit] = upsert(author)

  def update(id: Id, author: Author): IO[Unit] = upsert(author.copy(id = id))

  def find(id: Id): IO[Option[Author]] = repo.find(id)

  def list(pr: PageRequest, filter: Option[String]): IO[PageResponse[Author]] = repo.list(pr, filter)

  def delete(id: Id): IO[Unit] = repo.delete(id)

}
