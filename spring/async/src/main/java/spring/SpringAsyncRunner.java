package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutionException;

class SpringAsyncRunner {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var context = new AnnotationConfigApplicationContext("spring");
        var service = context.getBean(SpringAsyncService.class);
        service.doHeavyWork().join();
        service.apiCall().get();
    }

}
