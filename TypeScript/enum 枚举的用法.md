
```ts
export enum TodoFilterType {
  all,
  active,
  completed
}
```


        const keyname = filterTypeName as keyof typeof TodoFilterType;
        this.getTodos(TodoFilterType[keyname]);

let type = TodoFilterType.all;
let key: string = TodoFilterType[type]