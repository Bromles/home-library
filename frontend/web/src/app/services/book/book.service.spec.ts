import { TestBed } from '@angular/core/testing';

import { BookService } from './book.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {BookDto} from "./dto/book-dto";
import {environment} from "../../../environments/environment";
import {OAuthModule, OAuthService} from "angular-oauth2-oidc";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";

describe('BookService', () => {
  let service: BookService;
  let httpController: HttpTestingController;

  let url = environment.backendUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService, OAuthService, HttpClient]
    });
    service = TestBed.inject(BookService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAllBooks', () => {
    let mockBookDto = new BookDto();
    service.getAllBooks().subscribe((res) => {
      expect(res).toEqual([mockBookDto]);
    });

    const req = httpController.expectOne({
      method: 'GET',
      url: `${url}book`,
    });

    req.flush([mockBookDto]);
  })

  it('createBook', () => {
    let mockBookDto = new BookDto();
    const formData: FormData = new FormData();
    formData.append('name', 'name');
    formData.append('author', 'author');
    formData.append('file', 'file');
    formData.append('img', 'img');
    formData.append('tagName', 'tagName');
    formData.append('category', 'category');
    formData.append('yearOfPublishing', 'yearOfPublishing');
    service.createBook(formData).subscribe((res) => {
      expect(res).toEqual(mockBookDto);
    });

    const req = httpController.expectOne({
      method: 'POST',
      url: `${url}book`,
    });

    req.flush(mockBookDto);
  })

  it('getBook', () => {
    let mockBookDto = new BookDto();
    service.getBook(1).subscribe((res) => {
      expect(res).toEqual(mockBookDto);
    });

    const req = httpController.expectOne({
      method: 'GET',
      url: `${url}book/1`,
    });

    req.flush(mockBookDto);
  })

  it('updateBook', () => {
    let mockBookDto = new BookDto();
    service.updateBook(1, mockBookDto).subscribe((res) => {
      expect(res).toEqual(mockBookDto);
    });

    const req = httpController.expectOne({
      method: 'PUT',
      url: `${url}book/1`,
    });

    req.flush(mockBookDto);
  })

  it('deleteBook', () => {
    let mockBookDto = new BookDto();
    service.deleteBook(1).subscribe((res) => {
      expect(res).toEqual(mockBookDto);
    });

    const req = httpController.expectOne({
      method: 'DELETE',
      url: `${url}book/1`,
    });

    req.flush(mockBookDto);
  })

});
