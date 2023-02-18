import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {TagDto} from "./dto/tag-dto";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(
    private httpClient: HttpClient,
  ) { }

  getAllTag(): Observable<TagDto[]> {
    return this.httpClient.get<TagDto[]>(environment.backendUrl + "/v1/tags")
  }
}
