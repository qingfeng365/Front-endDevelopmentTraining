# 07 如果用 Typescript 方式书写模型

[TOC]

目前 sequelize 还没有完全支持 Typescript,

现在的方式是针对 sequelize 4 版本的写法, 

以后 sequelize 5 可能支持的更好一些.


## 步骤说明

在使用前确认已安装: 

- @types/sequelize
- sequelize-auto 

### 第一步: 自动生成 Typescript 接口和模型定义

要点: 使用参数 -z

```
sequelize-auto -o "./automodels" -d <数据库名> -h <主机域名> -u <用户名> -p 3306 -x <密码> -e mysql -C -z
```

加入 `-z` 参数后, 会额外生成模型对应的 Instance, Attribute 定义

定义是放在 `db.d.ts` 声明文件中

XXX_Attribute : 是所有字段属性的声明
XXX_Instance : 是系统实例属性和方法,再加上 模型自身属性 的声明
XXX_Model : 是系统属性和方法,再加上 模型自身方法的声明

### 第二步: 在 model 目录创建 模型文件

创建模型文件后, 在 `/automodels/db.d.ts` 文件找到对应的 Instance, Attribute, 

复制到 模型文件中

如以下示例:

```ts
import * as Sequelize from 'sequelize';

export interface DogAttribute {
  id?: number;
  remoteCode?: string;
  dogId?: number;
  dogType?: string;
  cardno?: string;
  year?: number;
  month?: number;
  orgName?: string;
  createdAt?: Date;
  updatedAt?: Date;
  confirmServerIp?: string;
}
export interface DogInstance extends Sequelize.Instance<DogAttribute>, DogAttribute { }
export interface DogModel extends Sequelize.Model<DogInstance, DogAttribute> { }
```

`注意: 要处理大小写问题`

由于 sequelize-auto 生成的声明, 大小写不符合规范, 主要是 DogAttribute, DogInstance, DogModel 首字母没有大写, 要手动修改(重要)



### 第三步: 修改声明

#### 处理 Attribute 中 id 的声明

`id?: number;` 

增加 `?` , 原因是 `Model.build()` 时, 一般是无法提供 id 值的.

#### 在 DogModel 声明中增加 可自由定义的 接口声明

```ts
export interface DogModel extends Sequelize.Model<DogInstance, DogAttribute> {
  [key: string]: any;
}
```

这一步是为了第四步不会出现编译错误

#### 增加注释

```ts
/**
 * 远程安全认证狗
 *
 * @export
 * @interface DogAttribute
 */
export interface DogAttribute {
  id?: number;
  /** 远程认证号 */
  remoteCode?: string;
  dogId?: number;
  dogType?: string;
  cardno?: string;
  year?: number;
  month?: number;
  orgName?: string;
  createdAt?: Date;
  updatedAt?: Date;
  /** 绑定公网Ip */
  confirmServerIp?: string;
}
/**
 * 远程安全认证狗
 *
 * @export
 * @interface DogInstance
 * @extends {Sequelize.Instance<DogAttribute>}
 * @extends {DogAttribute}
 */
export interface DogInstance extends Sequelize.Instance<DogAttribute>, DogAttribute { }
/**
 * 远程安全认证狗
 *
 * @export
 * @interface DogModel
 * @extends {Sequelize.Model<DogInstance, DogAttribute>}
 */
export interface DogModel extends Sequelize.Model<DogInstance, DogAttribute> {
  [key: string]: any;
}
```

### 第四步: 准备正式定义模型

```ts
import { Application } from 'egg';
import * as Sequelize from 'sequelize';
import { DataTypes } from 'sequelize';
export interface DogAttribute {
  id?: number;
  /** 远程认证号 */
  remoteCode?: string;
  dogId?: number;
  dogType?: string;
  cardno?: string;
  year?: number;
  month?: number;ts
  orgName?: string;
  createdAt?: Date;
  updatedAt?: Date;
  /** 绑定公网Ip */
  confirmServerIp?: string;
}
export interface DogInstance extends Sequelize.Instance<DogAttribute>, DogAttribute { }
export interface DogModel extends Sequelize.Model<DogInstance, DogAttribute> {
  [key: string]: any;
}

export default (app: Application) => {
  const DataTypes: DataTypes = app.Sequelize.DataTypes;
  const sequelize: Sequelize.Sequelize = app.model;

  // tslint:disable-next-line:variable-name
  const Dog: DogModel = sequelize.define<DogInstance, DogAttribute>(
    'Dog', {

    },
    {
      freezeTableName: true,
      tableName: 'dog',
      underscored: false,
    });
  return Dog;
};

```

注意代码中:

`const sequelize: Sequelize.Sequelize = app.model;`

`const Dog: DogModel = sequelize.define<DogInstance, DogAttribute>`

### 第五步: 复制模型字段定义

从 `/automodels/dog.ts` 中复制字段定义

```ts
  // tslint:disable-next-line:variable-name
  const Dog: DogModel = sequelize.define<DogInstance, DogAttribute>(
    'Dog', {
      id: {
        type: DataTypes.INTEGER(11),
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
        field: 'id',
      },
      remoteCode: {
        type: DataTypes.STRING(200),
        allowNull: true,
        defaultValue: '',
        field: 'remoteCode',
      },
      dogId: {
        type: DataTypes.BIGINT,
        allowNull: true,
        defaultValue: '0',
        field: 'dogId',
      },
      dogType: {
        type: DataTypes.STRING(20),
        allowNull: true,
        defaultValue: '',
        field: 'dogType',
      },
      cardno: {
        type: DataTypes.STRING(20),
        allowNull: true,
        defaultValue: '',
        field: 'cardno',
      },
      year: {
        type: DataTypes.INTEGER(11),
        allowNull: true,
        defaultValue: '0',
        field: 'year',
      },
      month: {
        type: DataTypes.INTEGER(11),
        allowNull: true,
        defaultValue: '0',
        field: 'month',
      },
      orgName: {
        type: DataTypes.STRING(200),
        allowNull: true,
        defaultValue: '',
        field: 'orgName',
      },
      createdAt: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'createdAt',
      },
      updatedAt: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'updatedAt',
      },
      confirmServerIp: {
        type: DataTypes.STRING(255),
        allowNull: true,
        defaultValue: '',
        field: 'confirmServerIp'
      },
    },
    {
      freezeTableName: true,
      tableName: 'dog',
      underscored: false,
    });
```

### 第六步: 增加模型方法

#### 先声明方法的接口

```ts
export interface DogModel extends Sequelize.Model<DogInstance, DogAttribute> {
  [key: string]: any;
  /**
   * 读取狗信息按远程认证号
   *
   * @param {string} remoteCode 远程认证号 uuid
   * @returns {Promise<DogInstance>}
   * @memberof DogModel
   */
  findOneForJsonByRemoteCode?(remoteCode: string): Promise<DogAttribute>;

}
```

注意: 

- 方法名后面加 `?` , 否则 第四步 `const Dog: DogModel` 这种声明会出错.

- 返回结果如果是返回 实例对象 (带方法)

应写成:

```ts
  findOneForJsonByRemoteCode?(remoteCode: string): Promise<DogInstance>;
```

- 返回结果如果是返回 Json实例对象 (扁平化)

应写成:

```ts
  findOneForJsonByRemoteCode?(remoteCode: string): Promise<DogAttribute>;
```

区别只是智能提示, 以及对调用方更清楚返回的结果形式.


#### 增加引入 Promise

因为 sequelize 使用 bluebird , 增加引入 bluebird

```ts
import * as Promise from 'bluebird';
```

### 第七步:   实现方法

```ts
  Dog.findOneForJsonByRemoteCode = function findOneForJsonByRemoteCode(remoteCode: string): Promise<DogAttribute> {
    return Dog.findOne({ where: { remoteCode }, raw: true });
  };
```

- 实现时不用写方法注释, 因在接口已写

## 最终完整代码

```ts
import * as Promise from 'bluebird';
import { Application } from 'egg';
import { DataTypes } from 'sequelize';
import * as Sequelize from 'sequelize';
/**
 * 远程安全认证狗
 *
 * @export
 * @interface DogAttribute
 */
export interface DogAttribute {
  id?: number;
  /** 远程认证号 */
  remoteCode?: string;
  dogId?: number;
  dogType?: string;
  cardno?: string;
  year?: number;
  month?: number;
  orgName?: string;
  createdAt?: Date;
  updatedAt?: Date;
  /** 绑定公网Ip */
  confirmServerIp?: string;
}
/**
 * 远程安全认证狗
 *
 * @export
 * @interface DogInstance
 * @extends {Sequelize.Instance<DogAttribute>}
 * @extends {DogAttribute}
 */
export interface DogInstance extends Sequelize.Instance<DogAttribute>, DogAttribute { }
/**
 * 远程安全认证狗
 *
 * @export
 * @interface DogModel
 * @extends {Sequelize.Model<DogInstance, DogAttribute>}
 */
export interface DogModel extends Sequelize.Model<DogInstance, DogAttribute> {
  [key: string]: any;
  /**
   * 读取狗信息按远程认证号
   *
   * @param {string} remoteCode 远程认证号 uuid
   * @returns {Promise<DogInstance>}
   * @memberof DogModel
   */
  findOneForJsonByRemoteCode?(remoteCode: string): Promise<DogAttribute>;
}

export default (app: Application) => {
  const DataTypes: DataTypes = app.Sequelize.DataTypes;
  const sequelize: Sequelize.Sequelize = app.model;

  // tslint:disable-next-line:variable-name
  const Dog: DogModel = sequelize.define<DogInstance, DogAttribute>(
    'Dog',
    {
      id: {
        type: DataTypes.INTEGER(11),
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
        field: 'id',
      },
      remoteCode: {
        type: DataTypes.STRING(200),
        allowNull: true,
        defaultValue: '',
        field: 'remoteCode',
      },
      dogId: {
        type: DataTypes.BIGINT,
        allowNull: true,
        defaultValue: '0',
        field: 'dogId',
      },
      dogType: {
        type: DataTypes.STRING(20),
        allowNull: true,
        defaultValue: '',
        field: 'dogType',
      },
      cardno: {
        type: DataTypes.STRING(20),
        allowNull: true,
        defaultValue: '',
        field: 'cardno',
      },
      year: {
        type: DataTypes.INTEGER(11),
        allowNull: true,
        defaultValue: '0',
        field: 'year',
      },
      month: {
        type: DataTypes.INTEGER(11),
        allowNull: true,
        defaultValue: '0',
        field: 'month',
      },
      orgName: {
        type: DataTypes.STRING(200),
        allowNull: true,
        defaultValue: '',
        field: 'orgName',
      },
      createdAt: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'createdAt',
      },
      updatedAt: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'updatedAt',
      },
      confirmServerIp: {
        type: DataTypes.STRING(255),
        allowNull: true,
        defaultValue: '',
        field: 'confirmServerIp'
      },
    }, {
      freezeTableName: true,
      tableName: 'dog',
      underscored: false,
    });

  Dog.findOneForJsonByRemoteCode = function findOneForJsonByRemoteCode(remoteCode: string): Promise<DogAttribute> {
    return Dog.findOne({ where: { remoteCode }, raw: true });
  };

  return Dog;
};

```

## 如何调用

```ts
import { DogModel } from '../../model/dog';

// 控制器方法内部
    // tslint:disable-next-line:variable-name
    const DogModel: DogModel = ctx.model.Dog;
    await DogModel.build(
      {
        remoteCode,
        dogId,
        dogType: '2',
        cardno,
        year,
        month,
        orgName: orgname,
        confirmServerIp,
      })
      .save()
      .then((res) => {
        ctx.logger.info('createProdog 创建数据成功... \n',
          res.get({ plain: true }));
        const result = {
          cardno,
          orgname,
          remoteCode,
          dogId,
          dogIdHex: dogId.toString(16).toUpperCase(),
          yearmonth,
          confirmServerIp,
        };
```

```ts
import { DogModel } from '../../model/dog';

      // tslint:disable-next-line:variable-name
      const DogModel: DogModel = ctx.model.Dog;

      const dog = await DogModel.findOneForJsonByRemoteCode(remoteCode);

```


