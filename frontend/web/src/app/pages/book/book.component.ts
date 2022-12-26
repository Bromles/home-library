import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../services/book/book.service";
import {BookDto} from "../../services/book/dto/book-dto";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../environments/environment";
import {TagService} from "../../services/tag/tag.service";
import {CategoryService} from "../../services/category/category.service";
import {TagDto} from "../../services/tag/dto/tag-dto";
import {CategoryDto} from "../../services/category/dto/category-dto";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent {

  private readonly bookId: Number

  book?: BookDto
  bookUpdateForm: FormGroup
  tags: TagDto[] = []
  categories: CategoryDto[] = []

  url: string = environment.backendUrl

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,
    private tagService: TagService,
    private categoryService: CategoryService
  ) {
    this.bookId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadBook()
    this.bookUpdateForm = new FormGroup({
      name: new FormControl(this.book?.name, Validators.required),
      author: new FormControl(this.book?.author, Validators.required),
      tagName: new FormControl(this.book?.tagName, Validators.required),
      category: new FormControl(this.book?.category, Validators.required),
      yearOfPublishing: new FormControl(this.book?.yearOfPublishing, Validators.required),
    });
    tagService.getAllTag().subscribe({
      next: tags => {
        console.log(tags)
        this.tags = tags
      }
    });
    categoryService.getAllCategories().subscribe({
      next: categories => {
        console.log(categories)
        this.categories = categories
      }
    })
  }

  updateBook() {
    this.bookService.updateBook(this.bookId, new BookDto(this.bookUpdateForm.value))
      .subscribe({
        next: body => {
          console.log(body);
        },
        error: error => {
          console.log(error);
        }
      })
  }

  private loadBook() {
    this.bookService.getBook(this.bookId)
      .subscribe({
        next: body => {
          console.log(body);
          this.book = body;
          this.setForm()
        },
        error: error => {
          console.log(error);
        }
      })
  }

  private setForm() {
    this.bookUpdateForm = new FormGroup({
      name: new FormControl(this.book?.name, Validators.required),
      author: new FormControl(this.book?.author, Validators.required),
      tagName: new FormControl(this.book?.tagName, Validators.required),
      category: new FormControl(this.book?.category, Validators.required),
      yearOfPublishing: new FormControl(this.book?.yearOfPublishing, Validators.required),
    });
  }

}
