package la.seafish.mnca.app.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper {
    private int errCode;
    private String errMsg;
    private boolean successFlag;
    private Object data;

    public static ResponseWrapper markSuccess(){
        return new ResponseWrapper(0,null,true,null);
    }
    public static ResponseWrapper markSuccess(Object data){
        return new ResponseWrapper(0,null,true,data);
    }
    public static ResponseWrapper markFail(int errCode,String errMsg,Object data){
        return new ResponseWrapper(errCode,errMsg,false,data);
    }



}
