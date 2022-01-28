# Strategy Pattern

## Strategy Pattern 이란?

> behavior software design pattern that enables selecting an algorithm at runtime.
알고리즘을 런타임시 동적으로 선택할 수 있게 하는 행동디자인 패턴
> 
- 알고리즘을 정의하고 각각을 캡슐화하여 클라이언트와는 독립적으로 알고리즘을 변경할 수 있다.
- 어떠한 객체의 행위를 캡슐화하여 행위을 행하는 전략에 따라 쉽게 행위를 바꿀수 있도록 도와준다.
- `기능을 사용하는 부분` 과 `구현하는 부분` 을 명확히 분리하는것이 중요하다.

<aside>
💡 객체가 할수있는 행위들을 각각의 전략으로 만들어 놓고, 동적으로 행위의 방식(전략)을 바꾸는것 만으로 행위의 수정이 가능하게 만든 패턴

</aside>

---

## Strategy Pattern과 Solid 원칙

패턴을 소개할때 캡슐화라는 단어가 자주 사용되는걸 보면서 [SOLID원칙](https://hydev.tistory.com/23)과 연관이 되는 부분이 보였다.

- 객체의 사용과 구현의 책임을 분리하는 SRP의 원칙
- 기능확장이 유연하고 기능의 변경은 최소화(혹은 없거나) 하는 OCP 원칙
- 부모(interface)의 메서드를 자식에서 구현 및 재정의가 이루어지는 LSP원칙

---

## Use When

- 행위기반적 클래스들간의 차이가 필요할때
- 알고리즘의 다양한 버전, 변형이 요구될때
- 런타임에 행위가 정의될 필요가 있을때
- 조건문 사용이 복잡하고 유지하기 어려울때

---

## 구조

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdoHKYP%2FbtrrUO7PqZZ%2FAKqlcVKnyBS0cekxVJFhn1%2Fimg.png)

---

## 예시

네비게이션 앱을 예시로 선정했다. 

초기버전은 차량 이용자만 사용하여 목적지까지 차량이동경로 만을 보여주면 되었지만 경로탐색의 다양성을 위해 대중교통을 이용하는사람, 도보로 이동하는사람 등 다양한 경로탐색(행위)을 위해 Strategy pattern을 사용한다.

+ 의존성 주입에 Strategy Pattern을 구현한다

[https://github.com/donghyeon-dev/StrategyPattern-with-Springboot](https://github.com/donghyeon-dev/StrategyPattern-with-Springboot)

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FblZXHF%2FbtrrJTz7HCt%2FLqPNgS1XaiYckUQVKKbiK1%2Fimg.png)

1. Route라는 행위(경로검색)에 대한 Interface와 작성한다.
enum으로 정의된 RouteName을 이용해서 각각의 전략을 구분한다.
    
    ```java
    public interface Route {
    		
        String executeSearch();
    
        RouteName getRouteName();
    }
    
    public enum RouteName {
        WalkRoute,
        DriveRoute,
        PublicRoute
    }
    ```
    
2. 행위의 여러가지의 전략을 자식으로 구현해준다.
    
    ```java
    @Component
    public class PublicRoute implements Route{
    
        @Override
        public String executeSearch() {
    				// 대중교통과 관련한 알고리즘 적용
            return "Publicroute";
        }
    
        @Override
        public RouteName getRouteName() {
            return RouteName.PublicRoute;
        }
    }
    
    @Component
    public class DriveRoute implements Route{
    
        @Override
        public String executeSearch() {
    				// 운전과 관련한 알고리즘 적용
            return "DriveRoute";
        }
    
        @Override
        public RouteName getRouteName() {
            return RouteName.DriveRoute;
        }
    }
    
    @Component
    public class WalkRoute implements Route{
    
        @Override
        public String executeSearch() {
    				// 보행과 관련한 알고리즘 적용
            return "WalkRoute";
        }
    
        @Override
        public RouteName getRouteName() {
            return RouteName.WalkRoute;
        }
    }
    ```
    
3. RouteFactory를 Bean으로 만들고 모든 전략을 factory에 주입한다. 
동작 원리가 어떻게 되는지 파악을 못하겠네
    
    ```java
    @Component
    public class RouteFactory {
        // 모든 Route 전략을 주입
        private List<Route> routes;
    
        @Autowired
        public RouteFactory(List<Route> routeList){
            this.routes = routeList;
        };
    
        public Route findRoute(RouteName routeName){
            return routes.stream().filter(
                    route -> route.getRouteName().equals(routeName)
            ).findAny().get();
        }
    ```
    
4. Controller와 Service 작성
    
    ```java
    @RestController
    @RequiredArgsConstructor
    public class RouteController {
    
        private final RouteService routeService;
    
        @RequestMapping("/route")
        public String getRoute(@RequestBody RouteInput request) {
            return routeService.getRoute(request);
        }
    }
    ```
    
    ```java
    @Service
    @RequiredArgsConstructor
    public class RouteService {
    
        private final RouteFactory routeFactory;
    
        public String getRoute(RouteInput request){
            String routeStrategy = request.getStrategy();
            // 조건식을 덜 사용하기 위해 쓰는 패턴인데.. 예시니까..
            RouteName routeName = routeStrategy.equals("walk") ? RouteName.WalkRoute :
                    routeStrategy.equals("drive")? RouteName.WalkRoute :
                            RouteName.PublicRoute;
            Route route = routeFactory.findRoute(routeName);
            return route.executeSearch();
        }
    }
    ```
    
    ![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FM9bnm%2FbtrrF5uL1ns%2FEgBmpypPuFQTP4iDcMmQqk%2Fimg.png)
    

---

## 장점과 단점

### 장점

Context코드의 변경 없이 새로운 **전략의 유연한 추가**가 최대 장점이다. 요구사항이 변경되었거나 추가되었을때 기존 코드를 변경하지 않아도 된다는 점이 장점이며 객체지향 원칙에도 부합해 객체지향적 개발이 가능하게 도와준다.

### 단점

몇가지의 알고리즘만있고 거의 변경되지 않으면 분기가 더 편할지도 모른다. 또한 전략들로 애플리케이션 내 객체 수가 증가하게 된다.

---

## Strategy Pattern in Java API

Stategy Pattern을 사용하여 구현한 라이브러리가 있다고 한다.

### [Strategy](http://en.wikipedia.org/wiki/Strategy_pattern) (recognizeable by behavioral methods in an abstract/interface type which invokes a method in an implementation of a *different* abstract/interface type which has been *passed-in* as method argument into the strategy implementation)

- `[java.util.Comparator#compare()](http://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#compare-T-T-)`, executed by among others `Collections#sort()`.
- `[javax.servlet.http.HttpServlet](http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html)`, the `service()` and all `doXXX()` methods take `HttpServletRequest` and `HttpServletResponse` and the implementor has to process them (and not to get hold of them as instance variables!).
- `[javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)`

---

### 참고

[https://ravthiru.medium.com/strategy-design-pattern-with-in-spring-boot-application-2ff5a7486cd8](https://ravthiru.medium.com/strategy-design-pattern-with-in-spring-boot-application-2ff5a7486cd8)
[https://velog.io/@hsw0194/스프링-부트-어플리케이션의-전략-패턴Strategy-Design-Pattern-with-in-Spring-Boot-application](https://velog.io/@hsw0194/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-%EC%96%B4%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98%EC%9D%98-%EC%A0%84%EB%9E%B5-%ED%8C%A8%ED%84%B4Strategy-Design-Pattern-with-in-Spring-Boot-application)

[https://velog.io/@ljinsk3/디자인-패턴-Strategy-Pattern](https://velog.io/@ljinsk3/%EB%94%94%EC%9E%90%EC%9D%B8-%ED%8C%A8%ED%84%B4-Strategy-Pattern)
