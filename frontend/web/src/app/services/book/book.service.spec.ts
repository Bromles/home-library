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

});
