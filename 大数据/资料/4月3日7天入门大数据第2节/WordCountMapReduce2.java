package com.cniao5;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * WR模板 与 优化
 */
public class WordCountMapReduce2 extends Configured implements Tool{
	
	/*
	 * Mapper Reducer Driver
	 */

	// step 1: Mapper
	public static class WordCountMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> { //使用hadoop默认数据类型
		
		private Text mapOutputKey = new Text();
		private IntWritable mapOutputValue = new IntWritable(1);

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			// line value
			String lineValue = value.toString();

			// spilt
			String[] strs = lineValue.split(" ");
			
			for(String str:strs) 
			{
				mapOutputKey.set(str);
				mapOutputValue.set(1);
				context.write(mapOutputKey, mapOutputValue);
				System.out.println("mapOutputKey+"+mapOutputKey+"===mapOutputValue+"+mapOutputValue);
			}
		}

	}

	// step 2: Reducer
	// Map的输出就是Reduce的输入
	public static class WordCountReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable outputValue = new IntWritable();

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			
			System.out.println("key+"+key+"===values+"+values);
			
			// temp sum
			int sum = 0;

			// iterator
			for (IntWritable value : values) {
				sum += value.get();
			}

			// set output
			outputValue.set(sum);

			context.write(key, outputValue);
			
			System.out.println("key+"+key+"===outputValue+"+outputValue);

		}

	}

	// step 3: Driver
	public int run(String[] args) throws Exception {

		Configuration configuration = this.getConf();

		Job job = Job.getInstance(configuration, this.getClass()
				.getSimpleName());
		job.setJarByClass(WordCountMapReduce2.class); //设置mapreduce入口


		// set job
		// input
		Path inpath = new Path(args[0]);
		FileInputFormat.addInputPath(job, inpath);

		// output
		Path outPath = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, outPath);

		// Mapper
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		/*
		 * shuffle优化
		 */
		//job.setPartitionerClass(cls);
		//job.setSortComparatorClass(cls);
		job.setCombinerClass(WordCountReducer.class);
		//job.setGroupingComparatorClass(cls);

		// Reducer
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// submit job -> YARN
		boolean isSuccess = job.waitForCompletion(true);
		return isSuccess ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {
		
		Configuration configuration = new Configuration();
		
		args = new String[] {
		"hdfs://lee01.cniao5.com:8020/user/root/mapreduce/input",
		"hdfs://lee01.cniao5.com:8020/user/root/mapreduce/output"};
		
		int status = ToolRunner.run(configuration, new WordCountMapReduce2(), args);

//		args = new String[] {
//				"hdfs://lee01.cniao5.com:8020/user/root/mapreduce/input",
//				"hdfs://lee01.cniao5.com:8020/user/root/mapreduce/output" };
//
//		// run job
//		int status = new WordCountMapReduce2().run(args);

		System.exit(status);
	}

}
