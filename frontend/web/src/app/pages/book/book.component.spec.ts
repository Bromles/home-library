import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BookComponent} from './book.component';
import {ActivatedRoute, convertToParamMap, Router} from "@angular/router";
import {BookModule} from "./book.module";
import {AppModule} from "../../app.module";
import {BookService} from "../../services/book/book.service";
import {BookDto} from "../../services/book/dto/book-dto";
import {Observable, of} from "rxjs";

describe('BookComponent', () => {
  let component: BookComponent;
  let fixture: ComponentFixture<BookComponent>;
  let router: Router;
  let bookService: BookService;

  const fakeActivatedRoute = {
    snapshot: {
      paramMap: convertToParamMap({
        id: '1'
      })
    }
  } as ActivatedRoute;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookComponent],
      imports: [AppModule, BookModule]
    }).compileComponents();

    fixture = TestBed.createComponent(BookComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    bookService = TestBed.inject(BookService);
    fixture.detectChanges();
  });

  it('should create', () => {
    spyOn(bookService, "getBook")
      .and.returnValue(of(new BookDto()))
    TestBed.createComponent(BookComponent);
    spyOn(router, 'navigate')
    expect(component).toBeTruthy();
  });


  it('havePermissions', () => {
    expect(component.havePermissions()).toBe(false)
  });

  it('updateBook', () => {
    spyOn(bookService, "updateBook")
      .and.returnValue(of(new BookDto()))
    component.updateBook()
  });

  it('updateBook error', () => {
    spyOn(bookService, "updateBook")
      .and.callThrough()
    component.updateBook()
  });

  it('deleteBook', () => {

    spyOn(router, 'navigate').and.stub()
    spyOn(bookService, "deleteBook")
      .and.returnValue(of(new BookDto()))
    component.deleteBook()
  });

  it('deleteBook', () => {

    component.deleteBook()
  });

  it('downloadMyFile', () => {
    // component.downloadMyFile()
  });
});
