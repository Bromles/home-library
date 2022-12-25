import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {FileUpload} from "primeng/fileupload";
import {BookService} from "../../services/book/book.service";

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.scss']
})
export class AddBookComponent implements OnInit, OnDestroy {
  @ViewChild('fileUpload') fileUpload: FileUpload | undefined;
  createBook: FormGroup;
  imagePreview: string | undefined;
  private subscription: Subscription | undefined;

  constructor(
      private httpClient: HttpClient,
      private bookService: BookService,
  ) {
    this.createBook = new FormGroup({
      name: new FormControl(null, Validators.required),
      author: new FormControl(null, Validators.required),
      file: new FormControl(null, Validators.required),
      tagName: new FormControl(null, Validators.required),
      category: new FormControl(null, Validators.required),
      yearOfPublishing: new FormControl(null, Validators.required),
    });
  }

  ngOnDestroy(): void {
        throw new Error('Method not implemented.');
    }

  ngOnInit(): void {
    this.subscription?.unsubscribe()
  }

  createCompany(): void {
    this.fileUpload!.upload();
    const formData: FormData = new FormData();
    formData.append('name', this.createBook.value.name);
    formData.append('author', this.createBook.value.author);
    formData.append('file', this.createBook.value.file);
    formData.append('tagName', this.createBook.value.tagName);
    formData.append('category', this.createBook.value.category);
    formData.append('yearOfPublishing', this.createBook.value.yearOfPublishing);
    this.subscription = this.bookService.createBook(formData)
  }

  onSelect(event: { files: any[]; }) {
    for (let file of event.files) {
      console.log(file)
      this.createBook.patchValue({ file: file });
      this.createBook.get('file')!.updateValueAndValidity();
    }
  }
}
