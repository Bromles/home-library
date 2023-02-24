import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {environment} from "../../../environments/environment";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-read',
  templateUrl: './read.component.html',
  styleUrls: ['./read.component.scss']
})
export class ReadComponent {

  bookId: Number
  url: string = environment.backendUrl
  heading: string = ""

  constructor(
    private route: ActivatedRoute,
  ) {
    this.bookId = Number(this.route.snapshot.paramMap.get('id'));
  }

  onError(error: any) {
    this.heading = "Неверный формат файла. Читалка поддерживает только pdf формат."
  }
}




