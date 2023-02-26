import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {BookDto} from "./dto/book-dto";
import {map, Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class BookService {


    constructor(
        private httpClient: HttpClient,
    ) {
    }

    getAllBooks(): Observable<BookDto[]> {
        return this.httpClient.get<BookDto[]>(environment.backendUrl + "book")
    }

    createBook(formData: FormData) {
        return this.httpClient.post(environment.backendUrl + "book", formData)
            .pipe(
                map((value: any) => {
                    console.log(value)
                    return value
                })
            )
    }

    getBook(bookId: number): Observable<BookDto> {
        return this.httpClient.get<BookDto>(environment.backendUrl + `book/${bookId}`)
    }

    updateBook(bookId: number, bookDto: BookDto) {
        return this.httpClient.put<BookDto>(environment.backendUrl + `book/${bookId}`, bookDto)
    }

    deleteBook(bookId: number) {
        return this.httpClient.delete(environment.backendUrl + `book/${bookId}`)
    }
}
