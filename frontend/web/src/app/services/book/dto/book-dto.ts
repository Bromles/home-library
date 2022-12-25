export class BookDto {
    id?: bigint;
    name?: string;
    author?: string;
    img?: any;
    tagName?:  string;
    category?: string;
    yearOfPublishing?: string;


    public constructor(init?: Partial<BookDto>) {
        Object.assign(this, init);
    }
}
