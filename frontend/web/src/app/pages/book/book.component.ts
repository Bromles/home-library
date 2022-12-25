import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../services/book/book.service";
import {BookDto} from "../../services/book/dto/book-dto";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent {

  private readonly bookId: Number

  book?: BookDto
  bookUpdateForm: FormGroup

  url: string = environment.backendUrl

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router
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
