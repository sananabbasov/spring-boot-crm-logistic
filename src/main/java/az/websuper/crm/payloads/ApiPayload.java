package az.websuper.crm.payloads;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiPayload<T> {
    private boolean success;
    private T data;
}
