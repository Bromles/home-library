import {TestBed} from '@angular/core/testing';

import {CategoryService} from "./category.service";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {BookDto} from "../book/dto/book-dto";
import {CategoryDto} from "./dto/category-dto";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {environment} from "../../../environments/environment";
import {AuthService} from "../auth.service";
import {OAuthService} from "angular-oauth2-oidc";

describe('CategoryService', () => {
  let service: CategoryService;
  let httpController: HttpTestingController;

  let url = environment.backendUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService, OAuthService, HttpClient]
    });
    service = TestBed.inject(CategoryService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAllCategories', () => {
    let mockCategoryDto = new CategoryDto();

    service.getAllCategories().subscribe((res) => {
      expect(res).toEqual([mockCategoryDto]);
    });

    const req = httpController.expectOne({
      method: 'GET',
      url: `${url}category`,
    });

    req.flush([mockCategoryDto]);
  })
});