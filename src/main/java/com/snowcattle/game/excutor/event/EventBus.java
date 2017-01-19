package com.snowcattle.game.excutor.event;

import com.snowcattle.game.excutor.update.IUpdate;
import com.snowcattle.game.excutor.utils.Loggers;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class EventBus implements IEventBus{

    private Map<EventType, Set<EventListener>> listenerMap;

    private Queue<IEvent> events;

    public EventBus() {
        this.listenerMap = new ConcurrentHashMap<EventType, Set<EventListener>>();
        this.events = new ConcurrentLinkedQueue<IEvent>();
    }

    public void addEventListener(EventListener listener) {
        Set<EventType> sets = listener.getSet();
        for (EventType eventType: sets){
            if(!listenerMap.containsKey(eventType)){
                listenerMap.put(eventType, new HashSet<EventListener>());
            }
            listenerMap.get(eventType).add(listener);
        }
    }

    public void removeEventListener(EventListener eventListener)   {
        Set<EventType> sets = eventListener.getSet();
        for (EventType eventType: sets){
            listenerMap.get(eventType).remove(eventListener);
        }
    }

    public void clearEventListener() {
        listenerMap.clear();
    }

    public void addEvent(IEvent event) {
        this.events.add(event);
    }

    public void handleEvent() {
        while (!events.isEmpty()){
            IEvent event = events.poll();
            if(event == null){
                break;
            }
            handleSingleEvent(event);
        }
    }

    /**
     *单次超过最大设置需要停止
     * 并且返回调度了多少事件
     * @param maxSize
     */
    public int cycle(int maxSize) {
        int i = 0;
        while (!events.isEmpty()){
            IEvent event = events.poll();
            if(event == null){
                break;
            }
            handleSingleEvent(event);
            i++;
            if(i > maxSize){
                break;
            }
        }

        return i;
    }

    public void handleSingleEvent(IEvent event) {

        if(Loggers.utilLogger.isDebugEnabled()) {
            EventParam[] eventParams = event.getParams();
            if(eventParams[0].getT() instanceof  IUpdate) {
                IUpdate iUpdate = (IUpdate) eventParams[0].getT();
                Loggers.utilLogger.debug("handle" + iUpdate.getId() + " dispatch");
            }
        }

        EventType eventType = event.getEventType();
        if(listenerMap.containsKey(eventType)){
            Set<EventListener> listenerSet = listenerMap.get(eventType);
            for(IEventListener eventListener:listenerSet){
                if(eventListener.containEventType(event.getEventType())) {
                    eventListener.fireEvent(event);
                }
            }
        }
    }

    public void clearEvent() {
        events.clear();
    }

    public void clear() {
        clearEvent();
        clearEventListener();
    }
}
