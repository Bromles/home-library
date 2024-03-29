import {TestBed} from '@angular/core/testing';

import {TagService} from './tag.service';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";
import {OAuthService} from "angular-oauth2-oidc";
import {TagDto} from "./dto/tag-dto";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {environment} from "../../../environments/environment";

describe('TagService', () => {
  let service: TagService;
  let httpController: HttpTestingController;

  let url = environment.backendUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService, OAuthService, HttpClient]
    });
    service = TestBed.inject(TagService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAllTag', () => {
    let mockTagDto = new TagDto();

    service.getAllTag().subscribe((res) => {
      expect(res).toEqual([mockTagDto]);
    });

    const req = httpController.expectOne({
      method: 'GET',
      url: `${url}tags`,
    });

    req.flush([mockTagDto]);
  })
});
