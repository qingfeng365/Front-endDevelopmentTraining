# 2-todolist示例第06讲 重构成多用户版本

## 重构数据模型


### 增加 User 模型

`ng g cl todo/model/user`

`/src/app/todo/model/user.ts`

```ts
export class User {
  id: number;
  username: string;
  password: string;
}
```

### 修改 Todo 模型

`/src/app/todo/model/todo.ts`

```ts
export class Todo {
  id: string;
  desc: string;
  completed: boolean;
  userId: number;
}

```

### 修改模拟数据

`/src/app/mock-data/in-memory-data.service.ts`

```ts
import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Todo } from '../todo/model/todo';
import { User } from '../todo/model/user';

@Injectable()
export class InMemoryDataService implements InMemoryDbService {

  constructor() { }

  createDb(): {} {
    const todos: Todo[] = [
      {
        id: 'f823b191-7799-438d-8d78-fcb1e468fc78',
        desc: '签到',
        completed: true,
        userId: 1
      },
      {
        id: 'c316a3bf-b053-71f9-18a3-0073c7ee3b76',
        desc: '开会',
        completed: false,
        userId: 1
      },
      {
        id: '5894a12f-dae1-5ab0-5761-1371ba4f703e',
        desc: '聚餐',
        completed: true,
        userId: 2
      },
      {
        id: '0d2596c4-216b-df3d-1608-633899c5a549',
        desc: '培训',
        completed: true,
        userId: 1
      },
      {
        id: '0b1f6614-1def-3346-f070-d6d39c02d6b7',
        desc: '考试',
        completed: false,
        userId: 2
      },
      {
        id: 'c1e02a43-6364-5515-1652-a772f0fab7b3',
        desc: '工作',
        completed: false,
        userId: 1
      }
    ];
    const users: User[] = [
      {
        id: 1,
        username: '张三',
        password: '1111'
      },
      {
        id: 2,
        username: '李四',
        password: '2222'
      }
    ];

    return { todos, users };
  }
}
```

