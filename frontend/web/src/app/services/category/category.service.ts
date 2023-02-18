import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {CategoryDto} from "./dto/category-dto";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private httpClient: HttpClient,
  ) { }

  getAllCategories(): Observable<CategoryDto[]> {
    return this.httpClient.get<CategoryDto[]>(environment.backendUrl + "/v1/category")
  }
}
