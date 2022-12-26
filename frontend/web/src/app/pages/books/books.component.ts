import {Component, OnInit} from '@angular/core';
import {BookDto} from "../../services/book/dto/book-dto";
import {environment} from "../../../environments/environment";
import {BookService} from "../../services/book/book.service";
import {TagService} from "../../services/tag/tag.service";
import {TagDto} from "../../services/tag/dto/tag-dto";
import {CategoryDto} from "../../services/category/dto/category-dto";
import {CategoryService} from "../../services/category/category.service";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit {

  books: BookDto[] = []
  tags: TagDto[] = []
  categories: CategoryDto[] = []
  selectedTags: TagDto[] = []
  selectedCategories: CategoryDto[] = []


  filterBooks: BookDto[] = []

  retrievedImage?: any

  url: string = environment.backendUrl

  constructor(
    private bookService: BookService,
    private tagService: TagService,
    private categoryService: CategoryService
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
    this.getTags();
    this.getCategories()
  }

  filter() {
    console.log(this.selectedTags.length === 0)
    this.filterBooks =
      this.books.filter((book) =>
        (this.selectedTags.length === 0 || this.selectedTags.find((tag) => tag.name === book.tagName)) &&
        (this.selectedCategories.length === 0 || this.selectedCategories.find((category) => category.name === book.category))
      )
  }

  getTags() {
    this.tagService.getAllTag().subscribe({
      next: tags => {
        this.tags = tags
      }
    })
  }

  getCategories() {
    this.categoryService.getAllCategories().subscribe({
      next: categories => {
        this.categories = categories
      }
    })
  }
}
