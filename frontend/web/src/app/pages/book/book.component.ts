import {Component, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../services/book/book.service";
import {BookDto} from "../../services/book/dto/book-dto";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../environments/environment";
import {TagService} from "../../services/tag/tag.service";
import {CategoryService} from "../../services/category/category.service";
import {TagDto} from "../../services/tag/dto/tag-dto";
import {CategoryDto} from "../../services/category/dto/category-dto";
import {Subscription} from "rxjs";
import {AuthService} from "../../services/auth.service";

@Component({
    selector: 'app-book',
    templateUrl: './book.component.html',
    styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnDestroy {

    private readonly bookId: number

    book?: BookDto
    bookUpdateForm: FormGroup
    tags: TagDto[] = []
    categories: CategoryDto[] = []

    url: string = environment.backendUrl

    private subscriptionTags: Subscription;
    private subscriptionCategories: Subscription

    constructor(
        private bookService: BookService,
        private route: ActivatedRoute,
        private router: Router,
        private tagService: TagService,
        private categoryService: CategoryService,
        private authService: AuthService
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
        this.subscriptionTags = tagService.getAllTag().subscribe(tags => {
            console.log(tags)
            this.tags = tags
        });
        this.subscriptionCategories = categoryService.getAllCategories().subscribe(categories => {
            console.log(categories)
            this.categories = categories
        })
    }

    ngOnDestroy(): void {
        this.subscriptionTags.unsubscribe()
        this.subscriptionCategories.unsubscribe()
    }

    havePermissions(): boolean {
        return this.authService.getUsername() === this.book?.createdUserId
    }

    updateBook() {
        this.bookService.updateBook(this.bookId, new BookDto(this.bookUpdateForm.value))
            .subscribe({
                next: body => {
                    console.log(body);
                },
                error: error => {
                    console.error(error);
                }
            })
    }

    deleteBook() {
        this.bookService.deleteBook(this.bookId)
          .subscribe({
              next: body => {
                  console.log(body);
                  this.router.navigate(['/books'])
              },
              error: error => {
                  console.error(error);
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
                    console.error(error);
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

    downloadMyFile() {
        const link = document.createElement('a');
        link.setAttribute('target', '_blank');
        link.setAttribute('href', this.url + `/${this.bookId}/file`);
        link.setAttribute('download', `${this.book?.name}`);
        document.body.appendChild(link);
        link.click();
        link.remove();
    }

}
