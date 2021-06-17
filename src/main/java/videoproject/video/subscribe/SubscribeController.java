package videoproject.video.subscribe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.subscribe.dto.SubscribeCreatorDto;
import videoproject.video.subscribe.dto.SubscribedDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;
    private final SubscribeRepository subscribeRepository;


    @PostMapping("/api/subscribe/subscribeNumber")
    public Map subscribeNumber(@RequestBody SubscribeCreatorDto subscribeCreatorDto) {
        System.out.println("구독자수 = " + subscribeCreatorDto);
        Map map = new HashMap<String, Object>();

        if(subscribeCreatorDto.getCreatorId() != null) {
            Long count = subscribeRepository.findSubscribeNumber(subscribeCreatorDto.getCreatorId());
            map.put("success", true);
            map.put("count", count);
        }

        System.out.println("JJJJJJJJJ" + map);
        return map;
    }

    @PostMapping("/api/subscribe/subscribed")
    public Map subscribed(@RequestBody SubscribedDto subscribedDto) {
        System.out.println(subscribedDto);
        Map map = new HashMap<String, Object>();
        SubScribe subscribe = subscribeRepository.findSubscribed(subscribedDto.getCreatorId(), subscribedDto.getSubscriberId());
        boolean result = false;

        if(subscribe != null) {
            result = true;
        }
        map.put("subscribed", result);
        map.put("success", true);

        return map;
    }

    @PostMapping("/api/subscribe/unSubscribe")
    public Map unsubscribe(@RequestBody SubscribedDto subscribedDto) {
        System.out.println("unsubscribe" + subscribedDto);

        subscribeService.unsubscribe(subscribedDto);

        Map map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    @PostMapping("/api/subscribe/subscribe")
    public Map subscribe(@RequestBody SubscribedDto subscribedDto) {
        System.out.println("subscribe = " + subscribedDto);

        subscribeService.subscribe(subscribedDto);
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        return map;

    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private boolean success;
    }
}
