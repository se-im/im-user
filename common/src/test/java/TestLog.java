import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
public class TestLog
{
    @Test
    public void test1()
    {
        //Logger logger = LoggerFactory.getLogger(getClass());

        log.info("ddd");
    }
}
