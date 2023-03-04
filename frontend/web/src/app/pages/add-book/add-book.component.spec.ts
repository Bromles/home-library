import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBookComponent } from './add-book.component';
import {AppModule} from "../../app.module";
import {AddBookModule} from "./add-book.module";
import {HttpTestingController} from "@angular/common/http/testing";
import {environment} from "../../../environments/environment";

describe('AddBookComponent', () => {
  let component: AddBookComponent;
  let fixture: ComponentFixture<AddBookComponent>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule, AddBookModule],
      declarations: [ AddBookComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('createBook', () => {
    component.createBook()
  });

  it('onSelect', () => {
    component.onSelect({files: [1]})
  });

  it('onSelectImg', () => {
    component.onSelectImg({files: [1]})
  });


});
