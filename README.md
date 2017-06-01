# protocol-engine

## 项目背景
目前java解析二进制字节流没有好的类库封装，我们在解析字节流的协议时，需要按照字段顺序逐个手动抠出，<br>
费事费力且很容易出错，受C语言解析二进制字节流的启发，封装了此Java类库，欢迎大家使用！
## 再开始介绍这个框架时，首先约定一下协议的定义方式
	X1 X2 X3 ... Xn
X1：代表第一个字节<br>
X2：代表第二个字节<br>
Xn: 代表第n个字节
## C语言协议解析简介	
假如我们有个这样的协议需要解析：<br>
	X1 X2 X3 X4 X5 X6
X1 X2：长度16Bit 小端格式<br>
X3 X4：长度16Bit 小端格式<br>
X5： 长度1个字节 <br>
X6： 长度一个字节<br>
那么我们用C语言会以如下方式来解析	
<pre>
struct protocol_example
{
	unsigned short column1,
	unsigned short column2,
	unsigned char column3,
	unsigned char column4
}
code example:
unsigned char[] recv_buf = ...;
protocol_example* example = (protocol_example*) &recv_buf;
if(example->column1 == 355) {
	dosomeing();
}
// decode protocol end
</pre>

