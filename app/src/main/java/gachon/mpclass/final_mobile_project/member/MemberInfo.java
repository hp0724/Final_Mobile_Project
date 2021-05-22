package gachon.mpclass.final_mobile_project.member;


 //회원 정보 저장
public class MemberInfo {

    private String name;
    private String phoneNumber;
    private String Date;


    public MemberInfo() {}


    public MemberInfo(String name, String phoneNumber  , String Date)
    {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.Date=Date;

    }
     public MemberInfo(String name, String phoneNumber     )
     {
         this.name=name;
         this.phoneNumber=phoneNumber;
     }





    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getDate(){
        return Date;
    }






}

