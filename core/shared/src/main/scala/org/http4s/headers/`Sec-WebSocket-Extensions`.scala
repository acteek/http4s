/*
 * Copyright 2013 http4s.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.http4s.headers

import cats.data.NonEmptyList
import cats.parse.{Parser, Rfc5234}
import org.http4s.{Header, ParseResult, headers}
import org.http4s.internal.parsing.{CommonRules, Rfc2616}
import org.typelevel.ci._

object `Sec-WebSocket-Extensions` {
  def apply(head: String, tail: String*): `Sec-WebSocket-Extensions` =
    apply(NonEmptyList(head, tail.toList))

  def parse(s: String): ParseResult[`Sec-WebSocket-Extensions`] =
    ParseResult.fromParser(parser, "Invalid Content-Language header")(s)

  private[http4s] val parser: Parser[`Sec-WebSocket-Extensions`] = ???

//  {
//    val languageTag: Parser[LanguageTag] =
//      (Parser.string(Rfc5234.alpha.rep) ~ (Parser.string("-") *> Rfc2616.token).rep0).map {
//        case (main: String, sub: collection.Seq[String]) =>
//          LanguageTag(main, QValue.One, sub)
//      }
//    CommonRules.headerRep1(languageTag).map { tags =>
//      headers.`Content-Language`(tags)
//    }
//  }

  implicit val headerInstance: Header[`Sec-WebSocket-Extensions`, Header.Recurring] =
    Header.createRendered(
      ci"Sec-WebSocket-Extensions",
      _.values,
      parse,
    )

  implicit val headerSemigroupInstance: cats.Semigroup[`Sec-WebSocket-Extensions`] =
    (a, b) => `Sec-WebSocket-Extensions`(a.values.concatNel(b.values))
}
//
//val name: CIString = ci"Access-Control-Allow-Methods"
//
//private[http4s] val parser =
//  CommonRules
//    .headerRep(CommonRules.token.map(CIString(_)))
//    .mapFilter { list =>
//      val parsedMethodList = list.traverse { ciMethod =>
//        Method.fromString(ciMethod.toString).toOption
//      }
//      parsedMethodList.map(list => apply(list.toSet))
//    }
//
//def parse(s: String): ParseResult[`Access-Control-Allow-Methods`] =
//  ParseResult.fromParser(parser, "Invalid Access-Control-Allow-Methods header")(s)
//
//implicit val headerInstance: Header[`Access-Control-Allow-Methods`, Header.Recurring] =
//  Header.createRendered(
//    name,
//    _.methods,
//    parse,
//  )
//
//implicit val headerSemigroupInstance: cats.Semigroup[`Access-Control-Allow-Methods`] =
//  (a, b) => `Access-Control-Allow-Methods`(a.methods ++ b.methods)
//}

// RFC - https://www.rfc-editor.org/rfc/rfc6455#section-11.3.2
final case class `Sec-WebSocket-Extensions`(values: NonEmptyList[String])
