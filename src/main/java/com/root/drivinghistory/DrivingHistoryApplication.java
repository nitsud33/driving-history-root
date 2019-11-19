package com.root.drivinghistory;

import com.root.drivinghistory.component.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import java.util.*;

@SpringBootApplication
public class DrivingHistoryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DrivingHistoryApplication.class, args);
	}

	@Autowired
	DrivingHistoryReport drivingHistoryReport;

	@Override
	public void run(String[] args){
		Scanner scanner = new Scanner(System.in);

		while(scanner.hasNext()){
			drivingHistoryReport.parse(scanner.nextLine());
		}

		drivingHistoryReport.report().forEach(System.out::println);
	}
}
