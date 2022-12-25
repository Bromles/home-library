import {Component, OnInit} from '@angular/core';
import {BookService} from "../../services/book/book.service";
import {BookDto} from "../../services/book/dto/book-dto";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  books: BookDto[] = []

  constructor(
    private bookService: BookService
  ) {
  }

  ngOnInit(): void {
    this.bookService.getAllBook()
      .subscribe({
        next: data => {
          this.books = data;
          console.log(data);
        },
        error: e => console.error(e)
      });
  }


}
