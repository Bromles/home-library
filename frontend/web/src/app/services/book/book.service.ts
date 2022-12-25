import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {BookDto} from "./dto/book-dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BookService {


  constructor(
    private httpClient: HttpClient,
  ) {
  }

  getAllBook(): Observable<BookDto[]> {
    return this.httpClient.get<BookDto[]>(environment.backendUrl + "book")
  }

  createBook(formData: FormData) {
    return this.httpClient.post(environment.backendUrl + "book", formData)
      .subscribe((value) => {
        console.log(value)
      })
  }

  getBook(bookId: Number): Observable<BookDto> {
    return this.httpClient.get<BookDto>(environment.backendUrl + `book/${bookId}`)
  }

  updateBook(bookId: Number, formData: BookDto) {
    return this.httpClient.put<BookDto>(environment.backendUrl + `book/${bookId}`, formData)
  }
}
