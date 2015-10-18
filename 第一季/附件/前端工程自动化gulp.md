# 前端工程自动化gulp

## glob模式语法

glob会默认返回文件和文件夹

- `*` 匹配任意数量的字符，但不匹配/ 
- `?` 匹配单个字符，但不匹配/
- `**` 匹配任意数量的字符，包括/，只要它是路径中唯一的一部分,使用特殊的**来递归返回所有子目录内的文件
- `{}` 允许使用一个逗号分割的列表或者表达式
- `!` 在模式的开头用于否定一个匹配模式(即排除与模式匹配的信息)

> `*` 不会匹配特殊的以.开头的隐藏文件（比如.gitignore, .DS_Store文件…），可以使用.*来匹配。

## Glob Primer (官方说明)

"Globs" are the patterns you type when you do stuff like `ls *.js` on
the command line, or put `build/*` in a `.gitignore` file.

Before parsing the path part patterns, braced sections are expanded
into a set.  Braced sections start with `{` and end with `}`, with any
number of comma-delimited sections within.  Braced sections may contain
slash characters, so `a{/b/c,bcd}` would expand into `a/b/c` and `abcd`.

The following characters have special magic meaning when used in a
path portion:

* `*` Matches 0 or more characters in a single path portion
* `?` Matches 1 character
* `[...]` Matches a range of characters, similar to a RegExp range.
  If the first character of the range is `!` or `^` then it matches
  any character not in the range.
* `!(pattern|pattern|pattern)` Matches anything that does not match
  any of the patterns provided.
* `?(pattern|pattern|pattern)` Matches zero or one occurrence of the
  patterns provided.
* `+(pattern|pattern|pattern)` Matches one or more occurrences of the
  patterns provided.
* `*(a|b|c)` Matches zero or more occurrences of the patterns provided
* `@(pattern|pat*|pat?erN)` Matches exactly one of the patterns
  provided
* `**` If a "globstar" is alone in a path portion, then it matches
  zero or more directories and subdirectories searching for matches.
  It does not crawl symlinked directories.

### Dots

If a file or directory path portion has a `.` as the first character,
then it will not match any glob pattern unless that pattern's
corresponding path part also has a `.` as its first character.

For example, the pattern `a/.*/c` would match the file at `a/.b/c`.
However the pattern `a/*/c` would not, because `*` does not start with
a dot character.

You can make glob treat dots as normal characters by setting
`dot:true` in the options.

### Basename Matching

If you set `matchBase:true` in the options, and the pattern has no
slashes in it, then it will seek for any file anywhere in the tree
with a matching basename.  For example, `*.js` would match
`test/simple/basic.js`.

### Negation

The intent for negation would be for a pattern starting with `!` to
match everything that *doesn't* match the supplied pattern.  However,
the implementation is weird, and for the time being, this should be
avoided.  The behavior is deprecated in version 5, and will be removed
entirely in version 6.

## gulp 语法

gulp只有五个方法： task，run，watch，src，和dest，在项目根目录新建一个js文件并命名为gulpfile.js，把下面的代码粘贴

## 实战示例

### 安装`gulp`

在项目根目录命令行

```
cnpm install gulp --save-dev
```

### 监听文件改变

在项目根目录新增 `gulpfile.js` 

```js
var gulp = require('gulp');

/**
 * [后端源文件数组]
 * @type {Array}
 */
var serverPath = [
  'server/**/*.js',
  'server/**/*.jade'
];

/**
 * [默认执行的任务]
 */
gulp.task('default', ['watch']);

/**
 * [description]
 * @param  {[type]} ){	gulp.watch(serverPath, f)}          [description]
 * @return {[type]}                            [description]
 */
gulp.task('watch',[], function(){
	gulp.watch(serverPath, function(event){
		console.log(event.path + ':' + event.type + ' ...');
	});
});

```

在项目根目录命令行:

```bash
gulp
```

修改 `server` 目录下的文件并保存，测试效果

### 监控文件改变+重启node+浏览器自动刷新

使用 `gulp-nodemon` + `browser-sync` 组合

安装 `gulp-nodemon` 和 `browser-sync`

在项目根目录命令行:

```bash
cnpm install gulp-nodemon --save-dev

cnpm install browser-sync --save-dev
```











