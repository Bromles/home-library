import {Component, OnInit} from '@angular/core';
import {BookDto} from "../../services/book/dto/book-dto";
import {environment} from "../../../environments/environment";
import {BookService} from "../../services/book/book.service";
import {TagService} from "../../services/tag/tag.service";
import {TagDto} from "../../services/tag/dto/tag-dto";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit {

  books: BookDto[] = []
  tags: TagDto[] = []
  selectedTags: TagDto[] = []

  filterBooks: BookDto[] = []

  retrievedImage?: any

  url: string = environment.backendUrl

  constructor(
    private bookService: BookService,
    private tagService: TagService,
  ) {
  }


  ngOnInit(): void {
    this.bookService.getAllBook()
      .subscribe({
        next: data => {
          this.books = data;
          this.filterBooks = data;
          this.retrievedImage = "data:image/png;" + this.books[0].img
          console.log(data);
        },
        error: e => console.error(e)
      });
    this.getTags()
  }

  filter() {
    console.log(this.selectedTags.length === 0)
    this.filterBooks =
      this.books.filter((book) =>
        this.selectedTags.length === 0 || this.selectedTags.find((tag) => tag.name === book.tagName)
      )
  }

  getTags() {
    this.tagService.getAllTag().subscribe({
      next: tags => {
        this.tags = tags
      }
    })
  }
}
