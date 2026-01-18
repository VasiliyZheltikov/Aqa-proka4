package utils;

import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Log4j2
public class TestListener implements ITestListener {

    public void onTestStart(ITestResult iTestResult) {
        log.info("======================================== STARTING TEST {} ========================================", iTestResult.getName());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        log.info("======================================== FINISHED TEST {} Duration: {} ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult));
    }

    public void onTestFailure(ITestResult iTestResult) {
        log.warn("======================================== FAILED TEST {} Duration: {} ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult));
    }

    public void onTestSkipped(ITestResult iTestResult) {
        log.info("======================================== SKIPPING TEST {} ========================================", iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private long getExecutionTime(ITestResult iTestResult) {
        return TimeUnit.MILLISECONDS.toSeconds(iTestResult.getEndMillis() - iTestResult.getStartMillis());
    }
}