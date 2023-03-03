import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BooksComponent } from './books.component';
import {AppModule} from "../../app.module";
import {BooksModule} from "./books.module";
import {of} from "rxjs";
import {BookDto} from "../../services/book/dto/book-dto";
import {BookService} from "../../services/book/book.service";
import {TagService} from "../../services/tag/tag.service";
import {CategoryService} from "../../services/category/category.service";
import {TagDto} from "../../services/tag/dto/tag-dto";
import {CategoryDto} from "../../services/category/dto/category-dto";

describe('BooksComponent', () => {
  let component: BooksComponent;
  let fixture: ComponentFixture<BooksComponent>;
  let bookService: BookService;
  let tagService: TagService;
  let categoryService: CategoryService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule, BooksModule],
      declarations: [ BooksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BooksComponent);
    component = fixture.componentInstance;
    bookService = TestBed.inject(BookService);
    tagService = TestBed.inject(TagService);
    categoryService = TestBed.inject(CategoryService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit', () => {
    spyOn(tagService, "getAllTag")
      .and.returnValue(of([new TagDto()]))
    spyOn(categoryService, "getAllCategories")
      .and.returnValue(of([new CategoryDto()]))
    spyOn(bookService, "getAllBooks")
      .and.returnValue(of([new BookDto()]))
    component.ngOnInit()
  });

  it('filter', () => {
    component.filter()
  });
});
