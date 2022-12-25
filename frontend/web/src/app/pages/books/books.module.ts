import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BooksComponent} from "./books.component";
import {DataViewModule} from "primeng/dataview";
import {BooksRoutingModule} from "./books-routing.module";
import {InputTextModule} from "primeng/inputtext";

@NgModule({
  declarations: [
    BooksComponent,
  ],
  imports: [
    CommonModule,
    DataViewModule,
    BooksRoutingModule,
    InputTextModule
  ]
})
export class BooksModule { }
