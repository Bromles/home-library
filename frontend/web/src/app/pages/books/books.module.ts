import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BooksComponent} from "./books.component";
import {DataViewModule} from "primeng/dataview";
import {BooksRoutingModule} from "./books-routing.module";
import {InputTextModule} from "primeng/inputtext";
import {DropdownModule} from "primeng/dropdown";
import {MultiSelectModule} from "primeng/multiselect";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    BooksComponent,
  ],
  imports: [
    CommonModule,
    DataViewModule,
    BooksRoutingModule,
    InputTextModule,
    DropdownModule,
    MultiSelectModule,
    FormsModule
  ]
})
export class BooksModule { }
