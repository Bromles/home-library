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
  @ViewChild('imgUpload') imgUpload: FileUpload | undefined;
  bookForm: FormGroup;
  imagePreview: string | undefined;
  private subscription: Subscription | undefined;

  constructor(
    private httpClient: HttpClient,
    private bookService: BookService,
  ) {
    this.bookForm = new FormGroup({
      name: new FormControl(null, Validators.required),
      author: new FormControl(null, Validators.required),
      file: new FormControl(null, Validators.required),
      img: new FormControl(null, Validators.required),
      tagName: new FormControl(null, Validators.required),
      category: new FormControl(null, Validators.required),
      yearOfPublishing: new FormControl(null, Validators.required),
    });
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.subscription?.unsubscribe()
  }

  createBook(): void {
    this.fileUpload!.upload();
    this.imgUpload!.upload();
    const formData: FormData = new FormData();
    formData.append('name', this.bookForm.value.name);
    formData.append('author', this.bookForm.value.author);
    formData.append('file', this.bookForm.value.file);
    formData.append('img', this.bookForm.value.img);
    formData.append('tagName', this.bookForm.value.tagName);
    formData.append('category', this.bookForm.value.category);
    formData.append('yearOfPublishing', this.bookForm.value.yearOfPublishing);
    this.subscription = this.bookService.createBook(formData)
  }

  onSelect(event: { files: any[]; }) {
    for (let file of event.files) {
      console.log(file)
      this.bookForm.patchValue({file: file});
      this.bookForm.get('file')!.updateValueAndValidity();
    }
  }

  onSelectImg(event: { files: any[]; }) {
    for (let file of event.files) {
      console.log(file)
      this.bookForm.patchValue({img: file});
      this.bookForm.get('img')!.updateValueAndValidity();
    }
  }
}
