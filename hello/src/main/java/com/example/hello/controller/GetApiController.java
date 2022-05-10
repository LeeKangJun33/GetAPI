package com.example.hello.controller;

import com.example.hello.dto.UserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/get")
public class GetApiController {

    @GetMapping(path="/hello") // http://loclhost:9090/api/get/hello
    public String getHello(){
        return "get Hello";
    }

    @RequestMapping(path="/hi",method = RequestMethod.GET) //get http://localhost:9090/api/get/hi
    public String hi(){
        return "hi";
    }

    //http://localhost:9090/api/get/path-varialbe/{spring-boot}
    //name을 일치시켜줄수없을떄 쓰는방법임
    @GetMapping("/path-variable/{id}")
    public String pathVariable(@PathVariable(name = "id") String pathName){

        System.out.println("PathVariable : "+pathName); //변수안에 name 이 동일해야한다.

        return pathName;
    }

    //QueryParametor & 연산자 기준으로 잘라보면됨

   //?key=value&key2=value2

   //http://localhost:9090/api/get/query-param?user=steve&email=steve@gmail.com&age=30
    //처음시작은 ? 부터 시작하고 그다음부터 key value가 온다

    @GetMapping(path="query-param")
    public String queryParam(@RequestParam Map<String, String> queryParam){ //RequestParam 어노테이션 잘 붙히기.
        StringBuilder sb =new StringBuilder(); //버퍼빌드
            //Map으로 받을때는 뭐가 받을지 모르는데 그럴떄는 모든 key를 받을수있음
        // 이렇게 되면 코딩할때 불편해짐 name이면 getobjectkey를 넣어서 하나하나 다 넣어야하는 귀찮음이있음
        queryParam.entrySet().forEach(entry-> { //람다식임
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("\n");

            sb.append(entry.getKey()+" = " + entry.getValue()+"\n");
        });


        return sb.toString();
    }

    @GetMapping("query-param02")
    public String queryParam02(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int age
    ){

        System.out.println(name);
        System.out.println(email);
        System.out.println(age);
        return name+" "+email+" "+age ;
    }
//명시적으로 변수를 받기위해서는 위에 코딩 으로 하면됨 RequestParam을 써서 각 변수를 받아주ㅠ면된다
    //나이에 문자열을 넣으면 에러가 난다 400에러가 나는데 400에러는 클라이언트의 잘못이다.

    //지금 하는 방법은 현업에서 자주 쓰고 많이쓰는 방법이고 추천하는 방법이다. dto형식으로 만드는 게
    //다른거랑 차이점은 RequestParm 어노테이션을 안썻다
    //spring boot에서 동작하는 원리가있는데 객체가 들어오게 되면은 QueryParma에 들어오는 주소들 즉,물음표 뒤에있는것들을 스프링부트에서 판단한다.
    //해당 객체에서 변수와 이름 매칭을해주게된다(name age email) 미리 QueryParma를 지정해주는게 편하기 때문이다.

    @GetMapping("query-param03")
    public String queryParam03(UserRequest userRequest)
    {
        System.out.println(userRequest.getName());
        System.out.println(userRequest.getEmail());
        System.out.println(userRequest.getAge());

        return userRequest.toString();
    }
}
//spring boot에서 get메서드를 받는 방법.
//가장 추천하는방법은 객체를 미리지정하는것을 추천한다 (get set 제네레이터)