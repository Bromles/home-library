import {Component, OnDestroy, OnInit} from '@angular/core';
import {BookDto} from "../../services/book/dto/book-dto";
import {environment} from "../../../environments/environment";
import {BookService} from "../../services/book/book.service";
import {TagService} from "../../services/tag/tag.service";
import {TagDto} from "../../services/tag/dto/tag-dto";
import {CategoryDto} from "../../services/category/dto/category-dto";
import {CategoryService} from "../../services/category/category.service";
import {Subscription} from "rxjs";

@Component({
    selector: 'app-books',
    templateUrl: './books.component.html',
    styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit, OnDestroy {

    books: BookDto[] = []
    tags: TagDto[] = []
    categories: CategoryDto[] = []
    selectedTags: TagDto[] = []
    selectedCategories: CategoryDto[] = []


    filterBooks: BookDto[] = []

    retrievedImage?: any

    url: string = environment.backendUrl

    private subscription: Subscription | undefined;

    constructor(
        private bookService: BookService,
        private tagService: TagService,
        private categoryService: CategoryService
    ) {
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe()
    }

    ngOnInit(): void {
        this.subscription = this.bookService.getAllBooks()
            .subscribe({
                next: data => {
                    this.books = data;
                    this.filterBooks = data;
                    this.retrievedImage = "data:image/png;" + this.books[0]?.img
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
        this.tagService.getAllTag().subscribe(tags => this.tags = tags)
    }

    getCategories() {
        this.categoryService.getAllCategories().subscribe(categories => this.categories = categories)
    }
}
