package pers.prover07.boot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Prover07
 * @classname User
 * @description TODO
 * @date 2022/1/30 20:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;

}
