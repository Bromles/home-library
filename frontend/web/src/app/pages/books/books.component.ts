import {Component, OnInit} from '@angular/core';
import {BookDto} from "../../services/book/dto/book-dto";
import {environment} from "../../../environments/environment";
import {BookService} from "../../services/book/book.service";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit {

  books: BookDto[] = []

  retrievedImage?: any

  url: string = environment.backendUrl

  constructor(
    private bookService: BookService
  ) {
  }

  ngOnInit(): void {
    this.bookService.getAllBook()
      .subscribe({
        next: data => {
          this.books = data;
          this.retrievedImage = "data:image/png;" + this.books[0].img
          console.log(data);
        },
        error: e => console.error(e)
      });
  }
}
