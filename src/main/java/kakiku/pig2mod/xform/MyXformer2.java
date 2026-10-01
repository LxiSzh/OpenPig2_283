package kakiku.pig2mod.xform;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class MyXformer2 implements ClassFileTransformer {
    private static String gDec = MyCheck.C.dec(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="));
    private static MyXformer2 gInstance = null;
    private static Set<String> gDoNotResetThisClass = new HashSet<>();
    public final List<String> mFullClassNamesOfProtectedFieldsSet = List.of(MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKDFEvtziLjnHf/XTcOsYWE="));
    public final List<String> mClassNameStartsOfProtectedFieldsSet = List.of(
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu8adde4+nRZ86zsNWLm7hIv3ZdA0i/DeQAoq1PPJRxOC"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScBs623bjbRg9fMhp+3tqsHyfcLqBGp5tqyObGcLU6Y1n"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScExlBwccePWEel3ztcYhqxc="),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu2NTW54a7s2XQvRLGETSpRiXdXT1GpfDMwtvjp57ok0h"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScGatuntb2Q7h/O7l+3/MJka8rI7e0XnXw1N8vIajoaWz"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcyZWEAAw5OoQjlGTxuKwCoyEbciGfXeXKgABiREPqTUNeA=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ4AoSWUJNbZc03ohfAs70OdWMc8lWrVyAW/22ucaAzqv"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcyYNUDu5hByPuImyR4Pz2f0f"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcyY4nvQ5IuVTtdDtUZ7nH3lK9XhAx0IxtAFG3ONU1pO9QQ=="),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcyYht93A/+iWJSOfsBzjyulH"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScClvrq4gqnx7kkCOd/BqVtwkUI6FtvtbrXesaluQ7sGhMR1QOMpMsIwPUz4grbMN5Q=="),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKEkrj7ZX56APuKlx+czxMrP9o4j9zEaMavxP7BFxcQq"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu2NTW54a7s2XQvRLGETSpRhNpIIWDqc3+cqzXKDbAOVm"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScJ09tyqIRgFCrjzlVdfHJCTMw/SaAP8Viou0rerD1o1d"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScHMdzu5hmt1/YH4k7Pfd8H/wCQ15RgJuc4BBmPOElBPn"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScIWbG6P4YwaC955Lmw4tPexLlyapM/qI7ytjpZ3WrjhA"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScGatuntb2Q7h/O7l+3/MJkbUBc0dIQq5jLEFejdpeATiCtqE2QPMAJMe4Hmj2h6/yA=="),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScJSqqj+k4OcLucyXSlAk4iERPjiy82BiiL51vlJhGtnr"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScI8Q3uNr03uE9dXV3IIRqtg="),
        MyCheck.C.dec("VjbfMPARc9Fot8i+VhTdxKSPjX+1bsKGjfH5veR9rkRm2vetMd3yP48HR9hfTbOH"),
        MyCheck.C.dec("IVNGqpeJOcY1aU+wQrHNh+Jeb5Y3JjZ6jSOzfsqc8J3r7eK56FbvWjK3Ebdot1et"),
        MyCheck.C.dec("IVNGqpeJOcY1aU+wQrHNh/5mqbDx11TmRET83j4pQsj1faF9Rh2NPtR6eLjgOc8o"),
        MyCheck.C.dec("IVNGqpeJOcY1aU+wQrHNh/4DYrkhU3nn3cAAHHOX0NG3dZbUBRYhjSH0xlck0iWl"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmAIA4KeyVmakNv5S6fLuUSSC7q4RJ+ctIk1plQFyYlx9"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmAOe+iOV0r2BT9Qf0XBTH8AACUoGjx29aVNRXfqb7sKu"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScClvrq4gqnx7kkCOd/BqVtwkUI6FtvtbrXesaluQ7sGhlIX2rOfaE1YVSr8BDtJd3w=="),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScClvrq4gqnx7kkCOd/BqVtztiW+zOOqIgOsARidueo5ZYijP3fsMTM7ACsIVLXQhXg=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ7oEzW8Dq+Cf6FE8uySoBHZmpDc/B1z5aMNkY7Clu44DJgecOZV0KHqJBBc/gJp/iw=="),
        MyCheck.C.dec("93LaYgt9s93SH8bwkxZeaI/uSYlBWjTcBpadbY/WlPE="),
        MyCheck.C.dec("93LaYgt9s93SH8bwkxZeaJwpf1uLg4nsCYjbVnhoYRBVVaojqja38roninsweVM1"),
        MyCheck.C.dec("93LaYgt9s93SH8bwkxZeaPoWxbbs6kh1l4fU9s//r97GbPmsbk3dSfPUptyENVSX"),
        MyCheck.C.dec("SSg69TV0XqispkkZUExoLg=="),
        MyCheck.C.dec("aoR5+NJbdpGPbRcsIVlBVS2HECXXKn4NcviA+oWIJEs="),
        MyCheck.C.dec("HX/NscF0PKhXw+WfY1rYKWba960x3fI/jwdH2F9Ns4c="),
        MyCheck.C.dec("GWJcVbVHleE6ibnvwVVKNZB6cF41rO/AhQbR2+Q4oz8="),
        MyCheck.C.dec("/UWT9bon+v029VyqcV1WFC3FSww/fje5NeyBwi2oPLk="),
        MyCheck.C.dec("ehVL35r+K6EF0p8N2Bkwv/q68SnVuv4r+u9kHcMkXD0cMEEHLJG65PjEr7M0umSD"),
        MyCheck.C.dec("Yq7BbJ2v/efu7wQGhHC2ZpGhUXupvnbKII0LqeyxFXdcQGQNb+HB1DigWUoZUtIb"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcyaeOmSJSeX8TASW5Pl2+y7cGBqq2r1if9muVeqOFjydUg=="),
        MyCheck.C.dec("ss0IDFK6dTy3Ft3+eBEQdQppHGTXzgwGIhLdmbCBm0I="),
        MyCheck.C.dec("XTbidulmPlqTf5O77aGxNA3nw6zkQseT79lLu/Y8rF0="),
        MyCheck.C.dec("lF4XMF0lblJW+IBDrfRDMQLFq28oIX9z5+AXPpzrNiEhhr5LNOy5AhOcoHimlEQcSJTcLbaf5oc8T+rOgnz2Ng=="),
        MyCheck.C.dec("lF4XMF0lblJW+IBDrfRDMQS+tIMFunpceNkYgkLg1bdW1jFMT2AQqIktJe+D78gEZtr3rTHd8j+PB0fYX02zhw=="),
        MyCheck.C.dec("lF4XMF0lblJW+IBDrfRDMQS+tIMFunpceNkYgkLg1bezKP6o7/QFa9lDRxkkcZE0Q05Vjk6lZt8phr3MqgSwzw=="),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu2NTW54a7s2XQvRLGETSpRgBVXrYnPA/fkpspq16l5Ig"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu5FYZ9YLm/2R5kfb6I0YEyGAHq5RoHTvGbtkoRfFXVxI"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcybXkLhreF+sDQc3mrM5aXh4"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmD5Fob6vGGz5Vk0o2ELRcyY/ggmEV2syBDN/OvXVzmD/UauUnJf38Uo5S2tyMZqWIw=="),
        MyCheck.C.dec("IeeO+SIgCC2AqSrw3GZZfCDoQ3sh3vOV69wTmPJ5mD08F6dF6Q5HWhQQBtc3h9EH"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu2NTW54a7s2XQvRLGETSpRiym4PktN4htdC9jLAxjIIb"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu3g+lpb6DSAERXAxJrTS3sMHQGXd61JMIvVi0sCC7SuA"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4PuzU86/hGVUa0KLedLY0ooNY0CfX6jJrzF90zpRi7f/Ap"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu6j7DnQBLMlpKUPhciz7QbYNTIjfN5G2ngJj5I8s8CQw"),
        MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu5FYZ9YLm/2R5kfb6I0YEyHOyzFNqN4qyxHLYJ4/Zmr8"),
        MyCheck.C.dec("BeR6CYxA0tU5xWNKQtKB0VRlhZXD/byrGlgL5Q5sjes="),
        MyCheck.C.dec("a21vlhUmk8fo4Ge5LKA44BxcOc0kSi5hqmMoRZQOB4o="),
        MyCheck.C.dec("VjbfMPARc9Fot8i+VhTdxKgZpdXrmBz0AXQh7kcpqFWgqwiaSAgkOPuWKTv3pDYE"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKSCxO3J3CKpHXHYWic6pzAXJUIV+igf3LN2mtzanZG3"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmOlwcLbfHyDnmQ2qkaNqgIPIzb3SR0GZeHxKMEqbzWGr"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKl6KJ5lO375wh4jk16H4G/WaX0reUnKVcV9paRfltRR"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKl6KJ5lO375wh4jk16H4G+I8Ga7rzXdcW8Jo9nHFg0o"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmLsEgP9JcqMpn1w5aTPrND1lO4n2bttToWXNIApB+9fZ"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmH99bv7C63iBY36ljrpYg5ZUAQX15K3IP1atPjRiijWh"),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmKEkrj7ZX56APuKlx+czxMo6lk1EG/Bon2m6Y9quIlIy"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScJTM8wUDAPp48Cdy0pcMA3ENmiKKNwVicP86v+OXOMTW"),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+b1iVJPIXIk6kPfNUewfMGr3zAvenSvey4RUwCN69AHqS"),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+bybuDdeuZk2dIlNb0SsjhvdXUWbjkauyPrCqWEjFRq7O"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScClvrq4gqnx7kkCOd/BqVtwGrFWmPPGsidYJBdCcc1NxZtr3rTHd8j+PB0fYX02zhw=="),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScNMqEWdLuWm9GJvnbtUb4A9taTPqiaKIn+Vf6G976sfz"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScJF7B8JyVHswgtXQmxmv0+4RAu+/7z2i142a6NLgPXyS"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScMmAY95rXCB5+kaXYJISdgAv1QA+Sb6WRjMnB8wzX3q8"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScGsuoWbdubEfOxSDalM4fNS5R/us2v5IxRkeUkOEaq86"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScD+zulXQ9ochrQn6ivddbcdm2vetMd3yP48HR9hfTbOH"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScLiwkhMbaMqhsNzTiBCBqZxm/yix5lX2VuYYxn1mW0lQ"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScLiwkhMbaMqhsNzTiBCBqZxxdLrtVopA2/bL3ahrxMeK"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScE47aReVnO+zEw+PpOfuwM7V5RTnILqidf5EVEyADYCNbG3T6kFcwQssBbumtJEJlQ=="),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScPxmXf4/16Tg8QOHWLoP+KvVh8leW84CjGJfaCKRQ9sA"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScKVt3ViZi8cG7nnRyxpqF9nhaemF7rB+5weEKfBVO9Wu"),
        MyCheck.C.dec("IVNGqpeJOcY1aU+wQrHNhwS68a3ngfYHW5oQ6rzqoufgPlcLao0Sjf1WrKnflg8d"),
        MyCheck.C.dec("IVNGqpeJOcY1aU+wQrHNh+Jeb5Y3JjZ6jSOzfsqc8J3Zdd3iLmEi8xSjLTo/GQd/"),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ9Z0AsNlI2se4SgFb2DfC5EoS777HO8bttI+nJTQihTGNAn1+oya8xfdM6UYu3/wKQ=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ97192l/CpJW7djAcFg7bpCvbvT9mZqvhlk1vMNEsHHNj17HsoTUEVnKYnn73SS+8g=="),
        MyCheck.C.dec("79pneHF1w69th+nfmt8iQg==")
    );
    public final List<String> mClassNameStartsOfProtectedFieldsGet = List.of(
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+bybuDdeuZk2dIlNb0SsjhvdXUWbjkauyPrCqWEjFRq7O"),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+b1iVJPIXIk6kPfNUewfMGr34U/9IJF9fblICLEh3xPoUmZTgoaXT2olZAzvASwW/mw=="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+b1iVJPIXIk6kPfNUewfMGr1jTA0w+PImrOkOfAOeM21NoiY/SH/YUzuHIhYMQLES8Q=="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+b1iVJPIXIk6kPfNUewfMGr0FQKZCN1hW+gzKVAVzZeYfswJ9q3VZiTeDkLmA/fCvgg=="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+b1iVJPIXIk6kPfNUewfMGr1zk+4w8ehJu0XukusiU65HmjDsHizHaoc5l0sYYrcIcQ=="),
        MyCheck.C.dec("79pneHF1w69th+nfmt8iQg==")
    );
    public final List<String> mBadCalleeNames = List.of(
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScAWVFEIkOZA/H2EsEeDdZQKv2o2YZhzNB3H2YkMRfTRJ"),
        MyCheck.C.dec("5nKB1q2GFJiRqyp+9HY16ZaoVSO3Tcc9vIjZSGfmZXHo/YFI75orUUM9D+TRwGaH"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScPWN74AXxORBX1ELNFPKDMimDA9rZVnc5mG6ofhFnBEE"),
        MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScJTM8wUDAPp48Cdy0pcMA3G8D2fVcszMZUIn/AAwuOwzdek6/P1l4NXgm1comNylqg=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJw3VJra+ZA9LGxaqn5GcE2Qgs93ZpiIMO6Nb0LMIbtoVZtr3rTHd8j+PB0fYX02zhw=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJw3VJra+ZA9LGxaqn5GcE2RPN5hIaelAquGFa5wD9Ex1"),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ367KKD7wOvchpkxlO3zYd4zFItvW0FvkBjLFG7LtRJ/ib4gqUmDULR8RIN+aVpBjw=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ367KKD7wOvchpkxlO3zYd76/OEIA5S9S6EYTC1HKCthKDkniJDdlY+dT1uSM7aIsw=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJxDWaeGMIk8b4mljvluNh3THNaxDRjFakpUR1DWC9vMXH6JSl/gXasympzAwvx7SNg=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ367KKD7wOvchpkxlO3zYd6rJnDtU+Cvx0ivX5KmCCsBxOe1dyupRNj+xtiaIcuGow=="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ9Z0AsNlI2se4SgFb2DfC5Ea52sONRejZEIZLcuEP8mLGgotfhS5vosmlKYfdHrOf92HevKSBvwNBhp5jg3RnLc="),
        MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmAIA4KeyVmakNv5S6fLuUSQaM8sIt+0FI1miR1IsfJQkgPHqyhH+GHo3ClRuXjsiwA=="),
        MyCheck.C.dec("QptoVhcA4TElTyTUbwhLFyniFg+2GIXdrehpgO+2vlhqby4WgqcLIkXCWQDxvaDS"),
        MyCheck.C.dec("mpXAYq7BJYzuGm7ra30QyJQ8TE1NcRruK+eT/ADKNc5M49G7Psxj5UWFo6EK9vs8"),
        MyCheck.C.dec("pqxwQNs92jJJkygXK/1ND870W+ztHO4EhGBvRl8TEpw="),
        MyCheck.C.dec("3nCMQeopWmlqsFEXpRRmazhLsYB8NhMTnAWH0lciWOc="),
        MyCheck.C.dec("OpdLmIHbeZvwi0P8bsUEF2OP2oSyzot1sWMMxrhbPTCIij0RsEu4/K6dZCcwDQJg"),
        MyCheck.C.dec("D6lciwtxEr/tjKvlNOYP18v8BttN0G1AKeEW7wSdow73/+sZ3CaxDN6gfbydLQAk"),
        MyCheck.C.dec("raIHuh198BJDdTNNPN23P9TmhXoK1zHEW01oF9wj5MmjEPzeTqOFBBDtxZbLOezg"),
        MyCheck.C.dec("raIHuh198BJDdTNNPN23P+5ECTmK0Xefef8INno60h14cMVKC16GEUyS4nHvldvr"),
        MyCheck.C.dec("D6lciwtxEr/tjKvlNOYP18exd+v+nNeSmNWnR/YLnz09T1lJNMdINP8ZxBHw0Z5I"),
        MyCheck.C.dec("raIHuh198BJDdTNNPN23P1k7cx5Ki8M9wuXup0vHuCWIMaQhZZ4+KYpXKHfWCuup"),
        MyCheck.C.dec("raIHuh198BJDdTNNPN23P7N+PV1CXWvAu3h+ypSV73x4cMVKC16GEUyS4nHvldvr"),
        MyCheck.C.dec("raIHuh198BJDdTNNPN23P7N+PV1CXWvAu3h+ypSV73x4cMVKC16GEUyS4nHvldvr"),
        MyCheck.C.dec("zxFMEZN0iL+EeYT0bbwAeyp9SIjib5PD18qOBoRmhRI="),
        MyCheck.C.dec("HX/NscF0PKhXw+WfY1rYKcqs64H2In83dbLAL3k/jb4="),
        MyCheck.C.dec("ihCavjHzE91nddXSqoNj55sTrAsDfQUY98emPARHXJKagydo4UzLINZqKDTPTSlV"),
        MyCheck.C.dec("ihCavjHzE91nddXSqoNj55sTrAsDfQUY98emPARHXJKkbvlrGm0GlD3J+dxT4PAl"),
        MyCheck.C.dec("ihCavjHzE91nddXSqoNj55sTrAsDfQUY98emPARHXJKjYk5AHJSUlncwuUPt9sfb"),
        MyCheck.C.dec("ihCavjHzE91nddXSqoNj55sTrAsDfQUY98emPARHXJLg+GxVd+N6CSqZG1d0immj"),
        MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsE5WGioTzk7DnN6bJ05ZSHusizsnR2x6Fo1Y7sJs4l0RmoMnaOFMyyDWaig0z00pVQ=="),
        MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsE5WGioTzk7DnN6bJ05ZSHuo4HT6sXgY9M2UHi6HuCqrRjLR9usKVtWSD1HRE/F1fA=="),
        MyCheck.C.dec("pqxwQNs92jJJkygXK/1ND06ILzWwTS77k21q7W2LwNo="),
        MyCheck.C.dec("pqxwQNs92jJJkygXK/1ND+ld6IIpmbCTsXQdpeDPr5Q="),
        MyCheck.C.dec("utnPyxQXwwvVQB6l0f4S8VdPyca6NKaI9g9F9IqVTho="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+b1iVJPIXIk6kPfNUewfMGr3zAvenSvey4RUwCN69AHqS"),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+bybuDdeuZk2dIlNb0SsjhveHF0EsFfS63LwQrG2qJfEqChqKe0vewTJUbKEbvYAWe5YaebNHZYctEPQ4G3OVseo="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+bybuDdeuZk2dIlNb0SsjhvdUsnOkCWTuAm39u/6b41U3ChqKe0vewTJUbKEbvYAWe5YaebNHZYctEPQ4G3OVseo="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+bybuDdeuZk2dIlNb0SsjhveHF0EsFfS63LwQrG2qJfEqgCwDOURm/4GbVy1VRT15/Q=="),
        MyCheck.C.dec("7KTeAm4koXJNmI9cL1N+bybuDdeuZk2dIlNb0SsjhvdPLrCgC7uDT3ZWCGP5wjVzGJjda5hkpmNMTRp6WIV/pogq6UQzW6mRIatfHbBWl2Q="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ9Z0AsNlI2se4SgFb2DfC5EoS777HO8bttI+nJTQihTG/PXSEuUDr62HMN5hUgQLxsTntXcrqUTY/sbYmiHLhqM="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ9Z0AsNlI2se4SgFb2DfC5FRkK1N+OW1zkpWsjO+OTpyDN9P94GQYFT/IplKMMeueN2HevKSBvwNBhp5jg3RnLc="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ9Z0AsNlI2se4SgFb2DfC5E0CbKY9aFO/QmB+gwBbuBSB/eKVEfteAELlEtIx1RYPEya4ehXkhP64+yqwHC6d4Q="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ9Z0AsNlI2se4SgFb2DfC5Ea52sONRejZEIZLcuEP8mLs1OuIUn5LsiKEhmSZVfI4Gba960x3fI/jwdH2F9Ns4c="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ97192l/CpJW7djAcFg7bpCrLHMKbJCLV7eLIi/aY8S+msoSN1h+LWwXK+5zAIm6a3uUrj7lcVIszmMHegXNsnM="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ97192l/CpJW7djAcFg7bpCPqPL5xm4bgqIZEQnuINFKwsT0UtZKX0hiu/6S0th31/UYdQ8vvfNKzhWO6TQTWY8="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ97192l/CpJW7djAcFg7bpCvbvT9mZqvhlk1vMNEsHHNPtnxHxFkwkR/zh6VHk1gjd2HevKSBvwNBhp5jg3RnLc="),
        MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ97192l/CpJW7djAcFg7bpCPqPL5xm4bgqIZEQnuINFKwsT0UtZKX0hiu/6S0th31+tUDxnEkIEuGuyi3fGciC18e/HsNXzUa4603hEKcvEM")
    );
    public static final Map<Class<?>, Object> gMapClassClassData = new ConcurrentHashMap<>();
    private static PrintWriter gMyLogFileWriter;

    public MyXformer2() {
        if (gInstance == null) {
            gInstance = this;
        }
    }

    public static MyXformer2 getInstance() {
        if (gInstance == null) {
            new MyXformer2();
        }

        return gInstance;
    }

    public static void doIt() {
        if (MyLib2.getKeisou() != null) {
            loadMyLib0();
            doASM();
        }
    }

    private static void loadMyLib0() {
        try {
            String myLib0Path = MyLib2.extractMyLib0Jar().toString();
            JarFile jarFile = new JarFile(new File(myLib0Path));
            MyLib2.getKeisou().appendToBootstrapClassLoaderSearch(jarFile);
            Class<?> clazz = Class.forName(MyCheck.C.dec("rxP82J5L085DNzxBKcq8XA=="), true, null);
            if (clazz.getClassLoader() == null) {
                Lookup lookup = MyLib2.getLookup();
                MethodHandle wrapMethodHandle = lookup.findStatic(
                    MyMethodHandles.class,
                    MyCheck.C.dec("aZyDEitBIuZxros18CzY6kiU3C22n+aHPE/qzoJ89jY="),
                    MethodType.methodType(MethodHandle.class, MethodHandle.class, String.class)
                );
                clazz.getMethod(MyCheck.C.dec("AZlA8E1RSHicicVlPBbwwF07JwV7CZi8T/sKz1Beg0w="), MethodHandle.class).invoke(null, wrapMethodHandle);
                clazz.getField(MyCheck.C.dec("vyJicM1JgpHTEYz9Xas7dmba960x3fI/jwdH2F9Ns4c=")).set(null, MyXformer2.class);
                clazz.getField(MyCheck.C.dec("W2LJ8vkKKIbkwlWHDi1Ujs1fgv7Dlyy68sNTOlThW1I=")).set(null, MyMethodHandles.class);
            }
        } catch (Throwable var5) {
        }
    }

    private static void doASM() {
        if (gInstance == null) {
            new MyXformer2();
            MyLib2.getKeisou().addTransformer(gInstance, true);
        }

        gInstance.loadJVMInnerClass();
        ArrayList<Class<?>> listOfTargetClass1st = new ArrayList<>();
        ArrayList<Class<?>> listOfTargetClass2nd = new ArrayList<>();

        for (Class<?> clazz1 : MyLib2.getKeisou().getAllLoadedClasses()) {
            if (!clazz1.getName().startsWith(MyCheck.C.dec("Tgu+8COwOLeDPYN2EoGP8w=="))
                && (MyLib2.isThisOtherBadMOD(clazz1) || MyLib2.isThisMyMOD(clazz1) || isThisNameTarget(clazz1.getName()))) {
                if ((
                        clazz1.getInterfaces().length <= 0
                            || !clazz1.getInterfaces()[0].getName().contains(MyCheck.C.dec("BG2AK/0x5GWLXoS9zOyVIJMSIHvxkXsoYhUAl+97VkQ="))
                    )
                    && !isThisNameTarget(clazz1.getName())) {
                    listOfTargetClass2nd.add(clazz1);
                } else {
                    listOfTargetClass1st.add(clazz1);
                }
            }
        }

        listOfTargetClass1st.addAll(listOfTargetClass2nd);

        for (Class<?> clazz2 : listOfTargetClass1st) {
            try {
                MyLib2.getKeisou().retransformClasses(clazz2);
            } catch (Throwable var6) {
            }
        }
    }

    private static boolean isThisNameTarget(String name) {
        if (name.startsWith(MyCheck.C.dec("ehVL35r+K6EF0p8N2Bkwv/q68SnVuv4r+u9kHcMkXD0cMEEHLJG65PjEr7M0umSD"))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ+FQP2N1Ym/Svlq6AXtuwCjhHQIVZY+HXG2oysUptFzbocwwTXglDkUvYaG8o5PrfQ=="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ3sginXXrvLKR2wJrAUDoFRd/t2wVcw1YDFqc78Moltf"))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ+FQP2N1Ym/Svlq6AXtuwCiebBipWwqG/VAHSX/nxgkjmhqe2YxVjV5Xau7Ji1eG4A=="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("7LaFDVqy20thFwAQ/xTSkC3DyVXj33/Zc3Ih5x+krREHABSdnfmB2t9MeoMFwH/E"))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("BfWEKzUwQGn/czMS13LTZbbBYZ4Mp2CRonWO8XNuKls="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("BfWEKzUwQGn/czMS13LTZfxQJSz61X3xAb4GWzMhepM="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("F+xPdnrYfZecloL5PQE7Vg=="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("utnPyxQXwwvVQB6l0f4S8Z8jRsnQlqfEjp3WrvhykHM="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsLqncoGEq3P55R22yKLCsEM="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("/wsCsh0ywu6DkjCO8A0ijw=="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("XTbidulmPlqTf5O77aGxNA3nw6zkQseT79lLu/Y8rF0="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("SM0SqabB/v0i/S7FNbx5+rPOao3fuqUnVWVt6/EKn7Q="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsF8ef0wCBuCwi6Eqf8P5TqU="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("pqxwQNs92jJJkygXK/1ND2ba960x3fI/jwdH2F9Ns4c="))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsDzMk5RQhp+2nURardEbylC4TramQ9ZS/W7Gc9brCxdT"))) {
            return true;
        } else if (name.startsWith(MyCheck.C.dec("OpdLmIHbeZvwi0P8bsUEF2OP2oSyzot1sWMMxrhbPTBaIA5qT6sT8U+rndO+xwAPTgbPdSETHSO/DUNgtV04CQ=="))) {
            return true;
        } else {
            return name.startsWith(MyCheck.C.dec("Nr4rAokwYkCKpdE2848X0aAS5kGi0RxJSrIFMA6IMYQ="))
                ? true
                : name.startsWith(MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScClvrq4gqnx7kkCOd/BqVtwkUI6FtvtbrXesaluQ7sGhlIX2rOfaE1YVSr8BDtJd3w=="));
        }
    }

    private void loadJVMInnerClass() {
        List<String> classNamesList = List.of(
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsEQiRp2o0C86fRkz7cJ9nhRwBC8uR8mMrrNv41g662R6mbj/b2WJlcDTUo3I2em9Ww=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsPqmBc2foAuaFpcVFgimA1rOeRkMNjicc4bVBob33wae3uKouMXY1XwgbVRCuRuZCQ=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsBmIhMbwFy2u/YwtNCGf1P5F7zEbLXQidpWeL5jyrviNBjK/z3MHexdeg41WSGfsjg=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsLG4CylUFAaQyV7eHy0o6/Vq3MARpK0yaGzLXO3TW0UGeYXnMG3JIyS0Ek6hnAGZbw=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsDScJ6tRDV0CYbtKLuphrZVF7zEbLXQidpWeL5jyrviNBjK/z3MHexdeg41WSGfsjg=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsG6tJeV9DLzyN+t3LONjtdhF7zEbLXQidpWeL5jyrviNBjK/z3MHexdeg41WSGfsjg=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsCOBeL5aCkeN9R9aTcjdqTOTVaPMU4BPvQutm6BC6ma5E6DiUzHc2oLxr1vsMDk8tA=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsJLUV8CBBbJ50Msk/ICGH22TVaPMU4BPvQutm6BC6ma5E6DiUzHc2oLxr1vsMDk8tA=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsPh9gf+8KvsV7GK9//xd1SNnQ0bi3Mt/nlBAXioMtpFl0RSrO0Bs/jgrB+KdtFlOqw=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsEQiRp2o0C86fRkz7cJ9nhSDxZfhQ9T70/cej7r2/1k+eYXnMG3JIyS0Ek6hnAGZbw=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsPqmBc2foAuaFpcVFgimA1ruH53ChmLRzEW+T1E/LJNlnxT2gnoWso++I57xlt483Q=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsBmIhMbwFy2u/YwtNCGf1P7abi3qiJ9X5+jKWQ+iyjD3Oj/mJFWvdn4cprUvzSVm+w=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsLG4CylUFAaQyV7eHy0o6/Wy3sPyojfPgyxCtyK8UO+5E6DiUzHc2oLxr1vsMDk8tA=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsDScJ6tRDV0CYbtKLuphrZXabi3qiJ9X5+jKWQ+iyjD3Oj/mJFWvdn4cprUvzSVm+w=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsG6tJeV9DLzyN+t3LONjtdjabi3qiJ9X5+jKWQ+iyjD3Oj/mJFWvdn4cprUvzSVm+w=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsCOBeL5aCkeN9R9aTcjdqTOcu+Lm5P08kgfNBQj73uCt3uKouMXY1XwgbVRCuRuZCQ=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsJLUV8CBBbJ50Msk/ICGH22cu+Lm5P08kgfNBQj73uCt3uKouMXY1XwgbVRCuRuZCQ=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsPh9gf+8KvsV7GK9//xd1SNbENYRT5NinJit1TrJ3q32BjK/z3MHexdeg41WSGfsjg=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsEQiRp2o0C86fRkz7cJ9nhRwBC8uR8mMrrNv41g662R6kg7bdYQUqDBc1PBxkRqFsg=="),
            MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsEQiRp2o0C86fRkz7cJ9nhSDxZfhQ9T70/cej7r2/1k+Rrxy4OsUghgJzqAhmGbBrA==")
        );

        try {
            for (String className : classNamesList) {
                Class.forName(className);
            }
        } catch (Throwable var4) {
        }
    }

    @Override
    public byte[] transform(
        Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer
    ) {
        return this.transform(className, classfileBuffer);
    }

    private byte[] transform(String className, byte[] classfileBuffer) {
        String classNamePeriod = className.replace('/', '.');
        if (!isThisNameTarget(classNamePeriod)) {
            if (MyLib2.isThisNameWhiteListedMOD(classNamePeriod)) {
                return null;
            }

            if (MyLib2.isThisNameMinecraftVanilla(classNamePeriod)) {
                return null;
            }

            if (!MyLib2.getModClassNamesFromJar().contains(classNamePeriod) && className.contains(MyCheck.C.dec("f9Re8R9nLJkjZ0l5l9HR6w=="))) {
                return null;
            }
        }

        if (!gDoNotResetThisClass.contains(classNamePeriod)) {
            byte[] classFile = MyLib2.getModClassDataFromJar().get(classNamePeriod);
            if (classFile != null) {
                classfileBuffer = classFile;
                if (MyLib2.isThisNameMyMOD(classNamePeriod)) {
                    return classFile;
                }
            }
        }

        byte[] classfileBufferVisitSmall = this.tranVisitSmall(className, classfileBuffer);
        if (classfileBufferVisitSmall != null) {
            classfileBuffer = classfileBufferVisitSmall;
        }

        byte[] classfileBufferNodesSmall = this.tranNodesSmall(className, classfileBuffer);
        if (classfileBufferNodesSmall != null) {
            classfileBuffer = classfileBufferNodesSmall;
        }

        byte[] classfileBufferVisitBig = this.tranVisitBig(className, classfileBuffer);
        if (classfileBufferVisitBig != null) {
            classfileBuffer = classfileBufferVisitBig;
        }

        byte[] classfileBufferNodesBig = this.tranNodesBig(className, classfileBuffer);
        if (classfileBufferNodesBig != null) {
            classfileBuffer = classfileBufferNodesBig;
        }

        return classfileBufferVisitSmall == null && classfileBufferNodesSmall == null && classfileBufferVisitBig == null && classfileBufferNodesBig == null
            ? null
            : classfileBuffer;
    }

    private byte[] tranVisitSmall(final String className, byte[] classfileBuffer) {
        String classNamePeriod = className.replace('/', '.');
        int readerFlag = 0;
        int writerFlag = 1;
        final AtomicBoolean bChanged = new AtomicBoolean(false);

        try {
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(writerFlag);
            reader.accept(
                new ClassVisitor(589824, writer) {
                    public MethodVisitor visitMethod(int access, final String name1, String descriptor1, String signature, String[] exceptions) {
                        if (signature == null) {
                            signature = MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==");
                        }

                        if (exceptions == null) {
                            exceptions = new String[0];
                        }

                        MethodVisitor mv = super.visitMethod(access, name1, descriptor1, signature, exceptions);
                        return new MethodVisitor(589824, mv) {
                            public void visitCode() {
                                super.visitCode();
                            }

                            public void visitMethodInsn(int opcode, String owner, String name2, String descriptor2, boolean isInterface) {
                                if (!name2.toLowerCase().contains(MyCheck.C.dec("fSjonOj3snJkmKYzPaGuIw=="))
                                    || !descriptor2.endsWith(MyCheck.C.dec("cVVz7EA7hMGxZU5n7spT9RUNA9mLIbxhp8NIaSEC87+BZ9YBRHPxlp0AMNyfzfNV"))
                                        && !descriptor2.endsWith(MyCheck.C.dec("nCJ6qs7rWJq1DyGyKh4m+Cbb9wAKcsAQinDgYfinUiK4Aj52OYCp8+jSmizSchq2"))) {
                                    if (className.equals(MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo6CiKb2Gd4LLVyEMiA5A+0ld/t2wVcw1YDFqc78Moltf"))
                                        && name1.equals(MyCheck.C.dec("nspisxFzmo3zYuhmc+hgFGyjupUj3m94NKuJsoxZrNE="))
                                        && owner.equals(MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2r9TO3FUVGIYLF+A+2A7N8k="))
                                        && name2.equals(MyCheck.C.dec("qtTTmEgxXvceMZ1MSu0vJg=="))) {
                                        bChanged.set(true);
                                        super.visitInsn(87);
                                        super.visitInsn(4);
                                    } else {
                                        super.visitMethodInsn(opcode, owner, name2, descriptor2, isInterface);
                                    }
                                } else {
                                    bChanged.set(true);
                                    super.visitMethodInsn(
                                        184,
                                        MyCheck.C.dec("8iVoJ5fhMV8nGSibbTJn1UzC2eyQTVHsZFfGbOyVdek="),
                                        MyCheck.C.dec("3k9JdyoSNbUYHTc3xiNZvyHybxbYsL3mvDXQlNQUyB0="),
                                        MyCheck.C.dec("w0CGlV5X1I8q/YWMuZF+bDIggHn0Y3ATK4wTZO6O0FxAU6VecmkXg5S5hMfRWNmz"),
                                        false
                                    );
                                }
                            }

                            public void visitInsn(int opcode) {
                                super.visitInsn(opcode);
                            }
                        };
                    }
                },
                readerFlag
            );
            if (bChanged.get()) {
                if (gDoNotResetThisClass.contains(classNamePeriod)) {
                }

                return writer.toByteArray();
            }
        } catch (Throwable var9) {
            if (bChanged.get()) {
            }
        }

        return null;
    }

    private byte[] tranVisitBig(final String className, byte[] classfileBuffer) {
        String classNamePeriod = className.replace('/', '.');
        if (MyLib2.isThisNameOtherBadMOD(classNamePeriod)) {
            return null;
        } else {
            int readerFlag = 8;
            int writerFlag = 3;
            final AtomicBoolean bChanged = new AtomicBoolean(false);

            try {
                ClassReader reader = new ClassReader(classfileBuffer);
                ClassWriter writer = new ClassWriter(writerFlag);
                reader.accept(
                    new ClassVisitor(589824, writer) {
                        public MethodVisitor visitMethod(int access, String name1, String descriptor, String signature, String[] exceptions) {
                            if (signature == null) {
                                signature = MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==");
                            }

                            if (exceptions == null) {
                                exceptions = new String[0];
                            }

                            MethodVisitor mv = super.visitMethod(access, name1, descriptor, signature, exceptions);
                            if (name1.startsWith(MyCheck.C.dec("fJpraJr81tGUFp9j+u+Ouw=="))
                                && descriptor.equals(MyCheck.C.dec("mVIi7RFNLVvCMVbd2JMvsMxwMBUOGH+bzJ+eUZEiLxn+er24dDHkNAxcTza9tw8CPz+VefXBzObycHDN1hjVjg=="))
                                )
                             {
                                bChanged.set(true);
                                return new MethodVisitor(589824, mv) {
                                    public void visitCode() {
                                        super.visitCode();
                                        Label loopStart = new Label();
                                        Label loopCheck = new Label();
                                        Label afterLoop = new Label();
                                        Label skipRemove = new Label();
                                        this.mv.visitInsn(1);
                                        this.mv.visitVarInsn(58, 1);
                                        this.mv.visitInsn(3);
                                        this.mv.visitVarInsn(54, 2);
                                        this.mv.visitLabel(loopCheck);
                                        this.mv.visitVarInsn(21, 2);
                                        this.mv.visitVarInsn(25, 0);
                                        this.mv
                                            .visitFieldInsn(
                                                180,
                                                MyCheck.C.dec("dUguFIvmWIrNTQsotNvs5Pq68SnVuv4r+u9kHcMkXD0cMEEHLJG65PjEr7M0umSD"),
                                                MyCheck.C.dec("OtB+UZn/ttuGf8FX4aOURmba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("bThJgpDJj+JyQzltCyQ0z/9xm0UGsnJmmMw9pNCs655yi1p4llylBsDbNZlKev89ljIkJi/6vf6ZxDRc5WRhZA==")
                                            );
                                        this.mv.visitInsn(190);
                                        this.mv.visitJumpInsn(162, afterLoop);
                                        this.mv.visitLabel(loopStart);
                                        this.mv.visitVarInsn(25, 0);
                                        this.mv
                                            .visitFieldInsn(
                                                180,
                                                MyCheck.C.dec("dUguFIvmWIrNTQsotNvs5Pq68SnVuv4r+u9kHcMkXD0cMEEHLJG65PjEr7M0umSD"),
                                                MyCheck.C.dec("OtB+UZn/ttuGf8FX4aOURmba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("bThJgpDJj+JyQzltCyQ0z/9xm0UGsnJmmMw9pNCs655yi1p4llylBsDbNZlKev89ljIkJi/6vf6ZxDRc5WRhZA==")
                                            );
                                        this.mv.visitVarInsn(21, 2);
                                        this.mv.visitInsn(50);
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("dUguFIvmWIrNTQsotNvs5Pq68SnVuv4r+u9kHcMkXD1xO/pZbb6xSMcTsS4SmXIYmjDsHizHaoc5l0sYYrcIcQ=="),
                                                MyCheck.C.dec("NX46weYjIYQdjRi64Mollw=="),
                                                MyCheck.C.dec("w0CGlV5X1I8q/YWMuZF+bNTZ/ZDVFgYNDl/8djPro2Pdnz+R5j5gbcgUY2mz7Be1"),
                                                false
                                            );
                                        this.mv.visitVarInsn(58, 3);
                                        this.mv.visitVarInsn(25, 3);
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                                MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                                false
                                            );
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                                false
                                            );
                                        this.mv.visitVarInsn(58, 4);
                                        this.mv.visitVarInsn(25, 4);
                                        this.mv.visitLdcInsn(MyCheck.C.dec("79pneHF1w69th+nfmt8iQg=="));
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                                false
                                            );
                                        this.mv.visitJumpInsn(154, skipRemove);
                                        this.mv.visitVarInsn(25, 4);
                                        this.mv.visitLdcInsn(MyCheck.C.dec("79pneHF1w69th+nfmt8iQg=="));
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                                false
                                            );
                                        this.mv.visitJumpInsn(154, skipRemove);
                                        this.mv.visitVarInsn(25, 3);
                                        this.mv.visitVarInsn(58, 1);
                                        this.mv.visitLabel(skipRemove);
                                        this.mv.visitIincInsn(2, 1);
                                        this.mv.visitJumpInsn(167, loopCheck);
                                        this.mv.visitLabel(afterLoop);
                                        this.mv.visitVarInsn(25, 1);
                                        Label skipRemoveCall = new Label();
                                        this.mv.visitJumpInsn(198, skipRemoveCall);
                                        this.mv.visitVarInsn(25, 0);
                                        this.mv.visitVarInsn(25, 1);
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("dUguFIvmWIrNTQsotNvs5Pq68SnVuv4r+u9kHcMkXD0cMEEHLJG65PjEr7M0umSD"),
                                                MyCheck.C.dec("zO5nks6SQ6wRe4WLhcfg6BwwQQcskbrk+MSvszS6ZIM="),
                                                MyCheck.C.dec("6whbVdd9NRuHhBq+/ZMS1boVxO7SRuOcAM+i2fHFUW+pWGdEmOXa+ooXZ91toEId"),
                                                false
                                            );
                                        this.mv.visitInsn(87);
                                        this.mv.visitLabel(skipRemoveCall);
                                        this.mv.visitVarInsn(25, 0);
                                        this.mv
                                            .visitFieldInsn(
                                                180,
                                                MyCheck.C.dec("dUguFIvmWIrNTQsotNvs5Pq68SnVuv4r+u9kHcMkXD0cMEEHLJG65PjEr7M0umSD"),
                                                MyCheck.C.dec("OtB+UZn/ttuGf8FX4aOURmba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("bThJgpDJj+JyQzltCyQ0z/9xm0UGsnJmmMw9pNCs655yi1p4llylBsDbNZlKev89ljIkJi/6vf6ZxDRc5WRhZA==")
                                            );
                                        this.mv.visitInsn(176);
                                    }
                                };
                            } else if (className.startsWith(
                                    MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo9MPeV/JdDPrGTp/DwIkvl9r9CP5pTSYzIV+W3XTF58OocwwTXglDkUvYaG8o5PrfQ==")
                                )
                                && name1.equals(MyCheck.C.dec("cCQ4Nbu+8yAbA6uP1YUnIGba960x3fI/jwdH2F9Ns4c="))
                                && descriptor.equals(MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="))) {
                                bChanged.set(true);
                                if (MyLib2.hasJar(MyCheck.C.dec("N8O4i+DZi5QX/2+juj/9mw=="))) {
                                }

                                return new MethodVisitor(589824, mv) {
                                    public void visitInsn(int opcode) {
                                        if (opcode == 177) {
                                            Label start = new Label();
                                            Label check = new Label();
                                            Label end = new Label();
                                            this.mv.visitLabel(start);
                                            this.mv.visitVarInsn(25, 0);
                                            this.mv
                                                .visitFieldInsn(
                                                    180,
                                                    MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo9MPeV/JdDPrGTp/DwIkvl9r9CP5pTSYzIV+W3XTF58OocwwTXglDkUvYaG8o5PrfQ=="),
                                                    MyCheck.C.dec("vZB4sLtdKQa91/+K1m2znw=="),
                                                    MyCheck.C.dec("1ryMdb8XH2mjTTJhywh9kWba960x3fI/jwdH2F9Ns4c=")
                                                );
                                            this.mv
                                                .visitMethodInsn(
                                                    185,
                                                    MyCheck.C.dec("RwABzRTHvPlcv+ORN6dQzg=="),
                                                    MyCheck.C.dec("6uu9FmflcIBas7SSthx4yw=="),
                                                    MyCheck.C.dec("HwNHFIYfvmPfJWFLY8n2oGbhx/kH8YKoxWCUMI6okPE="),
                                                    true
                                                );
                                            this.mv.visitVarInsn(58, 1);
                                            this.mv.visitLabel(check);
                                            this.mv.visitVarInsn(25, 1);
                                            this.mv
                                                .visitMethodInsn(
                                                    185,
                                                    MyCheck.C.dec("p54bTN0yBENNiuig7nYMiQ7GQmUTv4OBMygXEqgYlpA="),
                                                    MyCheck.C.dec("XgbI2zTc6D4HzgVjz7ceVQ=="),
                                                    MyCheck.C.dec("echQ/pNkYm3m0oxudd2+tw=="),
                                                    true
                                                );
                                            this.mv.visitJumpInsn(153, end);
                                            this.mv.visitVarInsn(25, 1);
                                            this.mv
                                                .visitMethodInsn(
                                                    185,
                                                    MyCheck.C.dec("p54bTN0yBENNiuig7nYMiQ7GQmUTv4OBMygXEqgYlpA="),
                                                    MyCheck.C.dec("yX+dfsI0ZiaVfmpLpxrNWA=="),
                                                    MyCheck.C.dec("MRiMRXTfYk/V2kjTMawG93VcYBquiniew7oUeKD1Ih8="),
                                                    true
                                                );
                                            this.mv.visitTypeInsn(192, MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSoykIYTkV41Hz1rEz1uUkoN+drReKLcRKtLVWEcpcJ44W"));
                                            this.mv.visitVarInsn(58, 2);
                                            this.mv.visitVarInsn(25, 2);
                                            this.mv
                                                .visitMethodInsn(
                                                    185,
                                                    MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSoykIYTkV41Hz1rEz1uUkoN+drReKLcRKtLVWEcpcJ44W"),
                                                    MyCheck.C.dec("j/iN/c8qowM474Yrijet2g=="),
                                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                                    true
                                                );
                                            this.mv.visitVarInsn(58, 3);
                                            Label if2 = new Label();
                                            Label doRemove = new Label();
                                            Label skipRemove = new Label();
                                            this.mv.visitVarInsn(25, 3);
                                            this.mv.visitLdcInsn(MyCheck.C.dec("N8O4i+DZi5QX/2+juj/9mw=="));
                                            this.mv
                                                .visitMethodInsn(
                                                    182,
                                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                    MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                                    MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                                    false
                                                );
                                            this.mv.visitJumpInsn(153, if2);
                                            this.mv.visitJumpInsn(167, doRemove);
                                            this.mv.visitLabel(if2);
                                            this.mv.visitVarInsn(25, 3);
                                            this.mv.visitLdcInsn(MyCheck.C.dec("d7d9qf04xbdJWO40rJtPCttU4vHdUAE6VF5Mx9qsL4s="));
                                            this.mv
                                                .visitMethodInsn(
                                                    182,
                                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                    MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                                    MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                                    false
                                                );
                                            this.mv.visitJumpInsn(153, skipRemove);
                                            this.mv.visitLabel(doRemove);
                                            this.mv.visitVarInsn(25, 1);
                                            this.mv
                                                .visitMethodInsn(
                                                    185,
                                                    MyCheck.C.dec("p54bTN0yBENNiuig7nYMiQ7GQmUTv4OBMygXEqgYlpA="),
                                                    MyCheck.C.dec("7SMdce/iu6Gzbeg5UvDv8w=="),
                                                    MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="),
                                                    true
                                                );
                                            this.mv.visitLabel(skipRemove);
                                            this.mv.visitJumpInsn(167, check);
                                            this.mv.visitLabel(end);
                                        }

                                        this.mv.visitInsn(opcode);
                                    }
                                };
                            } else if (className.startsWith(
                                    MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo9MPeV/JdDPrGTp/DwIkvl/kxsIqS9p4J96dimlMM7NSmhqe2YxVjV5Xau7Ji1eG4A==")
                                )
                                && name1.equals(MyCheck.C.dec("q70GLU7XHkBiYfbNarlwWA=="))
                                && descriptor.equals(
                                    MyCheck.C.dec(
                                        "eGrF+q48P0u9Hkt1/TEDXUlCb7DHqBZ0zQw/H3jobRsrJw0BD/4yfpWj3XRJ1Z5fm4lKWpK12yzErVEw3d2U4CWKsqMqT48KVzEx0JYuK6E="
                                    )
                                )) {
                                bChanged.set(true);
                                return new MethodVisitor(589824, mv) {
                                    public void visitCode() {
                                        this.mv.visitCode();
                                        this.mv.visitVarInsn(25, 0);
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("AcA+tzeBGEQbvzarVSzSo9MPeV/JdDPrGTp/DwIkvl/kxsIqS9p4J96dimlMM7NSCm3Kr2132x5BWW+GJKdqyA=="),
                                                MyCheck.C.dec("IjZiDF9hVUVdMTjpMyETFg=="),
                                                MyCheck.C.dec("z+Pyd4LIbWO0gxuAKV1Gafqd0yi1YhfK8CO+h6tlq28="),
                                                false
                                            );
                                        this.mv
                                            .visitMethodInsn(
                                                185,
                                                MyCheck.C.dec("hA56Kw77DreTHMcd+zJR6KQscEsnjB9Ui5BfMKfBtwU="),
                                                MyCheck.C.dec("yh0zrCYV+g21yCZabbxrIg=="),
                                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                                true
                                            );
                                        this.mv.visitLdcInsn(MyCheck.C.dec("zR/1t4F+5OgowrgeBY4U6A=="));
                                        this.mv.visitLdcInsn(MyCheck.C.dec("Wo+Eco5sTCT/PJejuDGBNQ=="));
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("ZWAdYkgQz7ejzRwx9w8PJA=="),
                                                MyCheck.C.dec(
                                                    "N/s8WWAOS3kmm0eEofL0ybvsz9otIkqKIE/qAM1KxW9qV3GcI/61466Vs61SwRwrfswS9T3zsVEz3OEjeG9k6d3S8ju3AECLfIuFO6rIbVU="
                                                ),
                                                false
                                            );
                                        this.mv.visitVarInsn(58, 1);
                                        this.mv.visitVarInsn(25, 1);
                                        this.mv.visitLdcInsn(MyCheck.C.dec("8Pu7qUUEkeHnQYOo/HrXV3t9INJuI6qthRCI1YWtBAg="));
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                                false
                                            );
                                        this.mv.visitVarInsn(25, 1);
                                        this.mv.visitLdcInsn(MyCheck.C.dec("GNJt6PLucBXxIVRoWsB/pA=="));
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                                false
                                            );
                                        this.mv.visitInsn(126);
                                        this.mv.visitVarInsn(54, 2);
                                        this.mv.visitVarInsn(25, 1);
                                        this.mv.visitLdcInsn(MyCheck.C.dec("V1WoJ4FLSZci2Bpcm4MIiQ=="));
                                        this.mv
                                            .visitMethodInsn(
                                                182,
                                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                                false
                                            );
                                        this.mv.visitVarInsn(54, 3);
                                        this.mv.visitVarInsn(21, 2);
                                        this.mv.visitVarInsn(21, 3);
                                        this.mv.visitInsn(128);
                                        this.mv.visitVarInsn(54, 4);
                                        Label continueLabel = new Label();
                                        this.mv.visitVarInsn(21, 4);
                                        this.mv.visitJumpInsn(154, continueLabel);
                                        this.mv
                                            .visitMethodInsn(
                                                184,
                                                MyCheck.C.dec("3Z36tJYJ2T4yUv0QxAT6V8/xIxQ7UPp1vmmlaNE4V7c="),
                                                MyCheck.C.dec("PT7Mnn/dSlDaYHp034fAzw=="),
                                                MyCheck.C.dec("v8ypZ9G3DbD9sezPOj3IR/ZIXC+iboQ1qykOR2U85nE="),
                                                false
                                            );
                                        this.mv.visitInsn(176);
                                        this.mv.visitLabel(continueLabel);
                                        this.mv.visitFrame(3, 0, null, 0, null);
                                        super.visitCode();
                                    }
                                };
                            } else {
                                return mv;
                            }
                        }
                    },
                    readerFlag
                );
                if (bChanged.get()) {
                    return writer.toByteArray();
                }
            } catch (Throwable var9) {
                if (bChanged.get()) {
                }
            }

            return null;
        }
    }

    private byte[] tranNodesSmall(String className, byte[] classfileBuffer) {
        boolean bChanged = false;

        try {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);
            bChanged |= this.tranNodes_transform(classNode);
            bChanged |= this.tranNodes_transformClassReader(classNode);
            bChanged |= this.tranNodes_transformClassNode(classNode);
            bChanged |= this.tranNodes_transformJavassist(classNode);
            bChanged |= this.tranNodes_javaAgent(classNode);
            bChanged |= this.tranNodes_unsafeClassReplace(classNode);
            bChanged |= this.tranNodes_dllString(classNode);
            bChanged |= this.tranNodes_dllCall(classNode);
            bChanged |= this.tranNodes_nativeMethod(classNode);
            bChanged |= this.tranNodes_osCommand(classNode);
            bChanged |= this.tranNodes_vmAttach(classNode);
            bChanged |= this.tranNodes_ifWin(classNode);
            bChanged |= this.tranNodes_methodHandle(classNode);
            bChanged |= this.tranNodes_varHandle(classNode);
            bChanged |= this.tranNodes_badLoop(classNode);
            bChanged |= this.tranNodes_fixVanilla(classNode);
            bChanged |= this.tranNodes_dllLoad(classNode);
            bChanged |= this.tranNodes_badCall(classNode);
            bChanged |= this.tranNodes_reflectionCallee(classNode);
            bChanged |= this.tranNodes_unsafePutCallee(classNode);
            bChanged |= this.tranNodes_unsafeGetCallee(classNode);
            bChanged |= this.tranNodes_hiddenCallee(classNode);
            bChanged |= this.tranNodes_dllLoadCallee(classNode);
            bChanged |= this.tranNodes_dllInvokeCallee(classNode);
            bChanged |= this.tranNodes_fixName1(classNode);
            if (bChanged) {
                ClassWriter cw = new ClassWriter(1);
                classNode.accept(cw);
                return cw.toByteArray();
            }
        } catch (Throwable var7) {
            if (bChanged) {
            }
        }

        return null;
    }

    private byte[] tranNodesBig(String className, byte[] classfileBuffer) {
        boolean bChanged = false;

        try {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 8);
            if (!MyLib2.isThisNameOtherMOD(classNode.name.replace('/', '.'))) {
                bChanged |= this.tranNodes_lookupCallee(classNode);
                bChanged |= this.tranNodes_methodHandleCallee(classNode);
                bChanged |= this.tranNodes_varHandleCallee(classNode);
            }

            if (bChanged) {
                ClassWriter cw = new ClassWriter(3);
                classNode.accept(cw);
                return cw.toByteArray();
            }
        } catch (Throwable var7) {
            if (bChanged) {
            }
        }

        return null;
    }

    private boolean tranNodes_transform(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                if (method.name.equals(MyCheck.C.dec("7WUPPIC9IQRyk+00UNU4+g=="))
                    && method.desc.endsWith(MyCheck.C.dec("XampBdlup22NTDIUD2qAiw=="))
                    && this.canMakeMethodEmpty(method)) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        } catch (Throwable var5) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_transformClassReader(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                boolean usingClassReader = false;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode methodInsn) {
                        if (methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrAjdtGlf3mX60cWXBOCEKWM="))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrH5DwiIObWIniKlFDVWsOfM="))) {
                            usingClassReader = true;
                            break;
                        }
                    } else if (insn instanceof FieldInsnNode fieldInsn
                        && (
                            fieldInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrAjdtGlf3mX60cWXBOCEKWM="))
                                || fieldInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrH5DwiIObWIniKlFDVWsOfM="))
                        )) {
                        usingClassReader = true;
                        break;
                    }
                }

                if (usingClassReader && this.canMakeMethodEmpty(method)) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        } catch (Throwable var12) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_transformClassNode(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                boolean usingClassNode = false;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode methodInsn) {
                        if (methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrPPNBR5agKdbTZTbiAf2jEhm2vetMd3yP48HR9hfTbOH"))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrJktPyN3fawNXnQdh65P1tQ="))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrO2xk4vvqjOK/q3++hVwWAlsbdPqQVzBCywFu6a0kQmV"))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrFloIhLu45DumfihGS8KsDw="))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrDG6ljoaMFOg59k1d6d+ptJFzwIiIK2Hsqw/tL3YTVFu"))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrGXBgq57fLnaCFM41GwWUSTOqWKirhdcK7oC2zYvYQdX"))
                            || methodInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrD+9GU0GQYYbQEyTQYy6mpxwoV2DeWmlisYa1MEBupf3"))) {
                            usingClassNode = true;
                            break;
                        }
                    } else if (insn instanceof FieldInsnNode fieldInsn
                        && (
                            fieldInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrPPNBR5agKdbTZTbiAf2jEhm2vetMd3yP48HR9hfTbOH"))
                                || fieldInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrO2xk4vvqjOK/q3++hVwWAlsbdPqQVzBCywFu6a0kQmV"))
                                || fieldInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrDG6ljoaMFOg59k1d6d+ptJFzwIiIK2Hsqw/tL3YTVFu"))
                                || fieldInsn.owner.equals(MyCheck.C.dec("6IW0CEV1u9Vmv5UsKJQSrD+9GU0GQYYbQEyTQYy6mpxwoV2DeWmlisYa1MEBupf3"))
                        )) {
                        usingClassNode = true;
                        break;
                    }
                }

                if (usingClassNode
                    && (
                        !classNode.name.startsWith(MyCheck.C.dec("Z7LSEwXTmSafUQsEeUw2y5D+h7bgQcY4E4/2+YRTUS4="))
                            || !MyLib2.hasJar(MyCheck.C.dec("Lh++4mYHuWyhENd+l5MI9w=="))
                    )
                    && (
                        !classNode.name.startsWith(MyCheck.C.dec("+2G2D5Fz07ZXvDVW0dDhOEkUHC59hnv6hiD4tAEhXZY="))
                            || !MyLib2.hasJar(MyCheck.C.dec("5uo+vHRciaiF0/CS/4eNvw=="))
                    )
                    && this.canMakeMethodEmpty(method)) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        } catch (Throwable var12) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_transformJavassist(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                boolean usingJavassist = false;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode methodInsn) {
                        if (methodInsn.owner.contains(MyCheck.C.dec("k/7iyOnwaK1CnKWLdoWXQAZsOlj1a7qt0NdRSfzQUnI="))
                            || methodInsn.owner.contains(MyCheck.C.dec("PYlTYne4miPaOXKc0OG/gORtg/qtunhfljvuX8h90kc="))
                            || methodInsn.owner.contains(MyCheck.C.dec("vyAZmmyZlj5DjZn8lEUlk+vXsHWTjSAL3dHSgcrrfvQ="))) {
                            usingJavassist = true;
                            break;
                        }
                    } else if (insn instanceof FieldInsnNode fieldInsn
                        && (
                            fieldInsn.owner.contains(MyCheck.C.dec("k/7iyOnwaK1CnKWLdoWXQAZsOlj1a7qt0NdRSfzQUnI="))
                                || fieldInsn.owner.contains(MyCheck.C.dec("PYlTYne4miPaOXKc0OG/gORtg/qtunhfljvuX8h90kc="))
                                || fieldInsn.owner.contains(MyCheck.C.dec("vyAZmmyZlj5DjZn8lEUlk+vXsHWTjSAL3dHSgcrrfvQ="))
                        )) {
                        usingJavassist = true;
                        break;
                    }
                }

                if (usingJavassist && this.canMakeMethodEmpty(method)) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        } catch (Throwable var12) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_javaAgent(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                if ((method.name.equals(MyCheck.C.dec("cgOORGbLv/5qaWmj4LmWhA==")) || method.name.equals(MyCheck.C.dec("NXQGiwWndumtglZtBe5Kdg==")))
                    && this.canMakeMethodEmpty(method)
                    && method.desc.equals(MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMHYkaAhMSlzrcME4IzT6ypnXNCgC1BtBPPqJURiYdgwJev4Dny9IImxBK11pC4zCWg=="))) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        } catch (Throwable var5) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_unsafeClassReplace(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                boolean usingPutIntVolatile = false;
                boolean usingGetIntVolatile = false;
                boolean usingAllocateInstance = false;
                boolean usingAddressSize = false;
                boolean usingPutByte = false;
                boolean usingReturn0XC3 = false;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode methodInsn && methodInsn.owner.contains(MyCheck.C.dec("tuosqwLErhk6M1gir2GSVQ=="))) {
                        if (methodInsn.name.startsWith(MyCheck.C.dec("hpQtaiWtLv2nURePk/xlvA=="))
                            || methodInsn.name.startsWith(MyCheck.C.dec("ypQoUu6nI8v9SXhwjl6bkQ=="))) {
                            usingPutIntVolatile = true;
                        } else if (methodInsn.name.startsWith(MyCheck.C.dec("OcydxhvkLqau7qqoA76ulA=="))
                            || methodInsn.name.startsWith(MyCheck.C.dec("HDl+3FyGAbgN6RW7qCs0NQ=="))) {
                            usingGetIntVolatile = true;
                        } else if (methodInsn.name.equals(MyCheck.C.dec("mD6WXtQc9Cgp+ZLdhe9QoWba960x3fI/jwdH2F9Ns4c="))) {
                            usingAllocateInstance = true;
                        } else if (methodInsn.name.equals(MyCheck.C.dec("mXxWmW5AeWZnHuCkTvY15w=="))) {
                            usingAddressSize = true;
                        } else if (methodInsn.name.startsWith(MyCheck.C.dec("MMfYAfFY8k+Ky+WHtePDcA=="))) {
                            usingPutByte = true;
                        }
                        continue;
                    }

                    if (insn instanceof IntInsnNode intInsnNode) {
                        if (intInsnNode.operand == 8) {
                            usingAddressSize = true;
                        } else if (intInsnNode.operand == 195 || intInsnNode.operand == -61) {
                            usingReturn0XC3 = true;
                        }
                    } else if (insn instanceof LdcInsnNode ldc) {
                        if (ldc.cst instanceof Long l && l == 8L) {
                            usingAddressSize = true;
                            continue;
                        }

                        if (ldc.cst instanceof Integer i && (i == 195 || i == -61)) {
                            usingReturn0XC3 = true;
                            continue;
                        }

                        if (ldc.cst instanceof String s) {
                            if (s.startsWith(MyCheck.C.dec("hpQtaiWtLv2nURePk/xlvA==")) || s.startsWith(MyCheck.C.dec("ypQoUu6nI8v9SXhwjl6bkQ=="))) {
                                usingPutIntVolatile = true;
                            } else if (s.startsWith(MyCheck.C.dec("OcydxhvkLqau7qqoA76ulA==")) || s.startsWith(MyCheck.C.dec("HDl+3FyGAbgN6RW7qCs0NQ=="))) {
                                usingGetIntVolatile = true;
                            } else if (s.equals(MyCheck.C.dec("mD6WXtQc9Cgp+ZLdhe9QoWba960x3fI/jwdH2F9Ns4c="))) {
                                usingAllocateInstance = true;
                            } else if (s.equals(MyCheck.C.dec("mXxWmW5AeWZnHuCkTvY15w=="))) {
                                usingAddressSize = true;
                            } else if (s.startsWith(MyCheck.C.dec("MMfYAfFY8k+Ky+WHtePDcA=="))) {
                                usingPutByte = true;
                            }
                        }
                    }
                }

                if (this.canMakeMethodEmpty(method)
                    && (usingPutIntVolatile && (usingAllocateInstance || usingGetIntVolatile) || usingPutByte && usingReturn0XC3)) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                    if (usingPutIntVolatile) {
                    }

                    if (usingPutByte) {
                    }
                }
            }
        } catch (Throwable var22) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_unsafePutCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("gIoR5H3VGbDgkaPVx5wz9g=="))
                || classNode.name.startsWith(MyCheck.C.dec("q0DYfPFmcEc+vcCqs+BAtbYJ/ZrsbO/vJqN3Da/WJNc="))) {
                for (MethodNode method : classNode.methods) {
                    if ((method.access & 256) == 0) {
                        if (method.desc.startsWith(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlgHAs6P6DRjUWdn0yexppJs="))
                            && (
                                method.name.startsWith(MyCheck.C.dec("hpQtaiWtLv2nURePk/xlvA=="))
                                    || method.name.startsWith(MyCheck.C.dec("vKJ4ajnAM13y00yoQtP0qA=="))
                                    || method.name.startsWith(MyCheck.C.dec("ypQoUu6nI8v9SXhwjl6bkQ=="))
                                    || method.name.startsWith(MyCheck.C.dec("3RzKxotFXNw9iwrRjnL5CQ=="))
                                    || method.name.contains(MyCheck.C.dec("O3mTHuQChe+K40h+fHufaA=="))
                                        && (
                                            method.name.contains(MyCheck.C.dec("tpCS+CB+fuj7tsqR0CmELg=="))
                                                || method.name.contains(MyCheck.C.dec("g6qUQoeGGdE56uTUdYUswA=="))
                                        )
                                    || method.name.equals(MyCheck.C.dec("M1eemMjWmHbLV74fA2KoVg=="))
                                    || method.name.startsWith(MyCheck.C.dec("g720yW9pzGezOT8UWfAARw=="))
                                    || method.name.startsWith(MyCheck.C.dec("gt7FLiNvScM+86Vx6FAwUQ=="))
                            )) {
                            InsnList insnList = new InsnList();
                            LabelNode L_continue = new LabelNode();
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(new JumpInsnNode(198, L_continue));
                            insnList.add(new VarInsnNode(22, 2));
                            insnList.add(new LdcInsnNode(8L));
                            insnList.add(new InsnNode(148));
                            insnList.add(new JumpInsnNode(154, L_continue));
                            if (method.desc.endsWith(MyCheck.C.dec("d/Na+OSfI7Lv+JitPp4oiQ=="))) {
                                insnList.add(new InsnNode(4));
                            } else {
                                this.insnListAdd0null(method.desc, insnList);
                            }

                            insnList.add(new InsnNode(Type.getReturnType(method.desc).getOpcode(172)));
                            insnList.add(L_continue);
                            method.instructions.insert(insnList);
                            bChanged = true;
                        }

                        if (method.desc.startsWith(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlgHAs6P6DRjUWdn0yexppJs="))
                            && (
                                method.name.startsWith(MyCheck.C.dec("nmjf/q4+4Mtn4kOKo+7vYA=="))
                                    || method.name.contains(MyCheck.C.dec("O3mTHuQChe+K40h+fHufaA=="))
                                    || method.name.startsWith(MyCheck.C.dec("/F3iv+cz0Te3E0lR3iuUXw=="))
                                    || method.name.equals(MyCheck.C.dec("M1eemMjWmHbLV74fA2KoVg=="))
                            )) {
                            InsnList insnList = new InsnList();
                            LabelNode L_continue = new LabelNode();
                            LabelNode L_isInstance = new LabelNode();
                            LabelNode L_endif = new LabelNode();
                            LabelNode L_popAndReturnAuto = new LabelNode();
                            LabelNode L_PopAndContinue = new LabelNode();
                            LabelNode L_end1stCheck = new LabelNode();
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(new JumpInsnNode(199, L_end1stCheck));
                            insnList.add(new VarInsnNode(22, 2));
                            insnList.add(new InsnNode(9));
                            insnList.add(new InsnNode(148));
                            insnList.add(new JumpInsnNode(154, L_end1stCheck));
                            this.insnListAdd0null(method.desc, insnList);
                            insnList.add(new InsnNode(Type.getReturnType(method.desc).getOpcode(172)));
                            insnList.add(L_end1stCheck);
                            if (method.name.equals(MyCheck.C.dec("XRPPwyUpEeyTD3UuI2JXcJuBT7b/gfjbtMW1MnhkYhQ="))) {
                                insnList.add(
                                    new MethodInsnNode(
                                        184,
                                        MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("fdzbK6qUW+78VdHx2tK/FQ=="),
                                        MyCheck.C.dec("Z0dHxxcJPly7JmcSCpnwe2O7mBIjcbDFHs89DfCagV0="),
                                        false
                                    )
                                );
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("FJA7d/gCp0ZfOUgZr+4kcw=="),
                                        MyCheck.C.dec("Xl8Hb2GG0zOWXTbAJ+ocy5yhXVuySZbfR7+ae2Qj/ARm2vetMd3yP48HR9hfTbOH"),
                                        false
                                    )
                                );
                                insnList.add(new InsnNode(5));
                                insnList.add(new InsnNode(50));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                                        MyCheck.C.dec("zFL1G6W6qIQGKn7NKhLfaw=="),
                                        MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                        false
                                    )
                                );
                                insnList.add(new InsnNode(89));
                                insnList.add(new LdcInsnNode(MyCheck.C.dec("p4bgRrQdUhK5yir766SA/riFOT8/jvUX0oXW/nzeXBuu9rl98QeCWHfD4giMZO+S")));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                        MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                        false
                                    )
                                );
                                insnList.add(new JumpInsnNode(154, L_PopAndContinue));
                                insnList.add(new InsnNode(87));
                            }

                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(new JumpInsnNode(198, L_continue));
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(new TypeInsnNode(193, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                            insnList.add(new JumpInsnNode(153, L_isInstance));
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(new TypeInsnNode(192, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                            insnList.add(new JumpInsnNode(167, L_endif));
                            insnList.add(L_isInstance);
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                    false
                                )
                            );
                            insnList.add(L_endif);
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            Map<String, List<String>> whiteList = new LinkedHashMap<>();
                            whiteList.put(
                                MyCheck.C.dec("XytUHKvEnZUX8z+VSttUmQ=="),
                                List.of(
                                    MyCheck.C.dec("hpQtaiWtLv2nURePk/xlvA=="),
                                    MyCheck.C.dec("+KYI6PqfaGnZD3VIZgROuA=="),
                                    MyCheck.C.dec("ypQoUu6nI8v9SXhwjl6bkQ==")
                                )
                            );
                            whiteList.put(MyCheck.C.dec("p4bgRrQdUhK5yir766SA/jGx/qxq0Cr9uBMYz/1cLd8="), List.of());
                            whiteList.put(
                                MyCheck.C.dec("n0cB55MNMnrBt5OWUSqRsQ=="),
                                List.of(
                                    MyCheck.C.dec("ShU5oW172fHUi/lGXpOO71/6QD0nAxjQR84WlEbqRSE="),
                                    MyCheck.C.dec("3RzKxotFXNw9iwrRjnL5CQ=="),
                                    MyCheck.C.dec("nZhxk0v4vlvMFvdxjEXqsg=="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptMGPKBMUtfYYDS7SCK6M00I="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptMQKP77MOoTgMwxeKYWxzRQ="),
                                    MyCheck.C.dec("nZhxk0v4vlvMFvdxjEXqsg=="),
                                    MyCheck.C.dec("hf/rdKyziXIOKTUZmqu2yWba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("qaeIFy3I0hqI87mZjewdlQ=="),
                                    MyCheck.C.dec("RDupHr0YCf5rZfJOgaf+MARHpk6QUQRr53uhF3m3LfQ="),
                                    MyCheck.C.dec("+KYI6PqfaGnZD3VIZgROuA=="),
                                    MyCheck.C.dec("hpQtaiWtLv2nURePk/xlvA=="),
                                    MyCheck.C.dec("g720yW9pzGezOT8UWfAARw=="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptCVfzdBMIymDiOuOaDd/+Is=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("IeeO+SIgCC2AqSrw3GZZfGRw3wuc5Zqgacd2IOklzBLb2VB+QTbD8+Oo+L9E7Qoe"),
                                List.of(
                                    MyCheck.C.dec("I/ASsGShTYzSSDZ1iGZl6w=="),
                                    MyCheck.C.dec("uWsiemNygaZkEXpIOm0Y9eLOE7AGD4pvCuBMRMvqmRI="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptGTlLcz7KCrwx0ULs7A+ps0="),
                                    MyCheck.C.dec("mPc5J878BQah6A1SjGeO1JsDRjEWEui+GxDX4yymiOE=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("yah8Uxkw5Dffr7Shv44FCg=="),
                                List.of(
                                    MyCheck.C.dec("5a/RBy1Hol+qcexZNz5tMw=="),
                                    MyCheck.C.dec("tw9Gu/XO+S31iFqbBvoYH2ba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("MMfYAfFY8k+Ky+WHtePDcA=="),
                                    MyCheck.C.dec("TxS94YHON4YFqqEWk+32T2ba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("y2xsNz0QkNXwWO8yp/qDSg=="),
                                    MyCheck.C.dec("/QnMw8ngPgnhEuC2Q/saa0lL0tlirPwrv9BJ4gfzKyk="),
                                    MyCheck.C.dec("cYxgDyANKROHAdt9SOo/8w==")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("se7C1sq4mFxYxczDhXHIsAAKord5nE2+DxUve1cBYys="),
                                List.of(
                                    MyCheck.C.dec("hf/rdKyziXIOKTUZmqu2yWba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("RDupHr0YCf5rZfJOgaf+MARHpk6QUQRr53uhF3m3LfQ=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("91Z6iPQSeqCwS0ZualPLIQ=="),
                                List.of(
                                    MyCheck.C.dec("hpQtaiWtLv2nURePk/xlvA=="),
                                    MyCheck.C.dec("ypQoUu6nI8v9SXhwjl6bkQ=="),
                                    MyCheck.C.dec("+KYI6PqfaGnZD3VIZgROuA==")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("0UF+Q6ACv83aW9lIGBZDVw=="),
                                List.of(
                                    MyCheck.C.dec("+KYI6PqfaGnZD3VIZgROuA=="),
                                    MyCheck.C.dec("uWsiemNygaZkEXpIOm0Y9eLOE7AGD4pvCuBMRMvqmRI="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptGTlLcz7KCrwx0ULs7A+ps0="),
                                    MyCheck.C.dec("g720yW9pzGezOT8UWfAARw=="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptCVfzdBMIymDiOuOaDd/+Is="),
                                    MyCheck.C.dec("WwWEd5hBXNfZzFE3S/Kn8r5FHeTp7VjfQEj4GSAnmJc="),
                                    MyCheck.C.dec("G6ZF0qkrgidrTavrEvYvmGxt0+pBXMELLAW7prSRCZU="),
                                    MyCheck.C.dec("DBQi1v8ZLFRXx/Nb6TAja6xtyBq1tvJnopEi8uKWN8U=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsDzMk5RQhp+2nURardEbylC4TramQ9ZS/W7Gc9brCxdT"),
                                List.of(
                                    MyCheck.C.dec("WwWEd5hBXNfZzFE3S/Kn8r5FHeTp7VjfQEj4GSAnmJc="),
                                    MyCheck.C.dec("G6ZF0qkrgidrTavrEvYvmGxt0+pBXMELLAW7prSRCZU="),
                                    MyCheck.C.dec("DBQi1v8ZLFRXx/Nb6TAja6xtyBq1tvJnopEi8uKWN8U="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptCVfzdBMIymDiOuOaDd/+Is="),
                                    MyCheck.C.dec("RDupHr0YCf5rZfJOgaf+MARHpk6QUQRr53uhF3m3LfQ=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsCHWn8puSqQ4f6LMV8er8jrjT9uLAvcrfVz7QhFyTP62"),
                                List.of(
                                    MyCheck.C.dec("WwWEd5hBXNfZzFE3S/Kn8r5FHeTp7VjfQEj4GSAnmJc="),
                                    MyCheck.C.dec("G6ZF0qkrgidrTavrEvYvmGxt0+pBXMELLAW7prSRCZU="),
                                    MyCheck.C.dec("DBQi1v8ZLFRXx/Nb6TAja6xtyBq1tvJnopEi8uKWN8U="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptCVfzdBMIymDiOuOaDd/+Is="),
                                    MyCheck.C.dec("RDupHr0YCf5rZfJOgaf+MARHpk6QUQRr53uhF3m3LfQ=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsO2NCCbrBxgpfJRjrQZIvKcMNkHAMfUy565tKqXghFGG"),
                                List.of(
                                    MyCheck.C.dec("WwWEd5hBXNfZzFE3S/Kn8r5FHeTp7VjfQEj4GSAnmJc="),
                                    MyCheck.C.dec("G6ZF0qkrgidrTavrEvYvmGxt0+pBXMELLAW7prSRCZU="),
                                    MyCheck.C.dec("DBQi1v8ZLFRXx/Nb6TAja6xtyBq1tvJnopEi8uKWN8U="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptCVfzdBMIymDiOuOaDd/+Is="),
                                    MyCheck.C.dec("RDupHr0YCf5rZfJOgaf+MARHpk6QUQRr53uhF3m3LfQ=")
                                )
                            );
                            whiteList.put(
                                MyCheck.C.dec("HK4JNW4PN96FQBvPZxJpK5l3m938stMmO17y+gNGjLSnMBYngfgHQDKddHSdgdvX"),
                                List.of(
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptGTlLcz7KCrwx0ULs7A+ps0="),
                                    MyCheck.C.dec("uWsiemNygaZkEXpIOm0Y9eLOE7AGD4pvCuBMRMvqmRI=")
                                )
                            );
                            whiteList.put(MyCheck.C.dec("oUGjpmikyIbk4HQOu2O57Xawao/T+DeCST1J01QKJYE="), List.of(MyCheck.C.dec("QIQIxq5rU5H+ohdcvP+aWw==")));
                            whiteList.put(
                                MyCheck.C.dec("utnPyxQXwwvVQB6l0f4S8fiVtiPyp/z1d1F78Ci7bCVJS9LZYqz8K7/QSeIH8ysp"),
                                List.of(MyCheck.C.dec("RDupHr0YCf5rZfJOgaf+MARHpk6QUQRr53uhF3m3LfQ="))
                            );
                            whiteList.put(
                                MyCheck.C.dec("PWZ2W2uWswoba83CiF+Jkh6oIdiXFyLy0MH8IOulyag="),
                                List.of(
                                    MyCheck.C.dec("WwWEd5hBXNfZzFE3S/Kn8r5FHeTp7VjfQEj4GSAnmJc="),
                                    MyCheck.C.dec("G6ZF0qkrgidrTavrEvYvmGxt0+pBXMELLAW7prSRCZU="),
                                    MyCheck.C.dec("DBQi1v8ZLFRXx/Nb6TAja6xtyBq1tvJnopEi8uKWN8U="),
                                    MyCheck.C.dec("ccJOFw6BFHr8Wu/92+eptCVfzdBMIymDiOuOaDd/+Is=")
                                )
                            );

                            for (Entry<String, List<String>> entry : whiteList.entrySet()) {
                                String className = entry.getKey();
                                List<String> methodNames = entry.getValue();
                                if (methodNames.isEmpty() || methodNames.contains(method.name)) {
                                    insnList.add(new InsnNode(89));
                                    insnList.add(new LdcInsnNode(className));
                                    insnList.add(
                                        new MethodInsnNode(
                                            182,
                                            MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                            MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                            MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                            false
                                        )
                                    );
                                    insnList.add(new JumpInsnNode(154, L_PopAndContinue));
                                }
                            }

                            for (String className : this.mFullClassNamesOfProtectedFieldsSet) {
                                insnList.add(new InsnNode(89));
                                insnList.add(new LdcInsnNode(className));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                        MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                        false
                                    )
                                );
                                insnList.add(new JumpInsnNode(154, L_popAndReturnAuto));
                            }

                            for (String className : this.mClassNameStartsOfProtectedFieldsSet) {
                                insnList.add(new InsnNode(89));
                                insnList.add(new LdcInsnNode(className));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                        MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                        false
                                    )
                                );
                                insnList.add(new JumpInsnNode(154, L_popAndReturnAuto));
                            }

                            insnList.add(new InsnNode(87));
                            insnList.add(new JumpInsnNode(167, L_continue));
                            insnList.add(L_popAndReturnAuto);
                            insnList.add(new InsnNode(87));
                            if (method.desc.endsWith(MyCheck.C.dec("d/Na+OSfI7Lv+JitPp4oiQ=="))) {
                                insnList.add(new InsnNode(4));
                            } else {
                                this.insnListAdd0null(method.desc, insnList);
                            }

                            insnList.add(new InsnNode(Type.getReturnType(method.desc).getOpcode(172)));
                            insnList.add(L_PopAndContinue);
                            insnList.add(new InsnNode(87));
                            insnList.add(L_continue);
                            method.instructions.insert(insnList);
                            bChanged = true;
                        }

                        if (method.name.startsWith(MyCheck.C.dec("MMfYAfFY8k+Ky+WHtePDcA==")) && method.desc.equals(MyCheck.C.dec("oEGzTwlNoc54VXeKblsmnw=="))) {
                            InsnList insnListx = new InsnList();
                            insnListx.add(new InsnNode(177));
                            method.instructions.insert(insnListx);
                            bChanged = true;
                        }
                    }
                }
            }
        } catch (Throwable var16) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_unsafeGetCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("gIoR5H3VGbDgkaPVx5wz9g=="))
                || classNode.name.startsWith(MyCheck.C.dec("q0DYfPFmcEc+vcCqs+BAtbYJ/ZrsbO/vJqN3Da/WJNc="))) {
                for (MethodNode method : classNode.methods) {
                    if ((method.access & 256) == 0
                        && method.desc.equals(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlkUfGxMkc5F1qMFAldPgkm6yod3IuqoekLyyLjY9UR9q"))
                        && (
                            method.name.startsWith(MyCheck.C.dec("y1zMFBT4RA3PRpD03W9dVQ=="))
                                || method.name.startsWith(MyCheck.C.dec("7z5tDt+ZwyBTjEUvUULI/Q=="))
                        )) {
                        InsnList insnList = new InsnList();
                        LabelNode L_continue = new LabelNode();
                        LabelNode L_isInstance = new LabelNode();
                        LabelNode L_endif = new LabelNode();
                        LabelNode L_popAndReturnAuto = new LabelNode();
                        LabelNode L_PopAndContinue = new LabelNode();
                        LabelNode L_end1stCheck = new LabelNode();
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new JumpInsnNode(199, L_end1stCheck));
                        insnList.add(new VarInsnNode(22, 2));
                        insnList.add(new InsnNode(9));
                        insnList.add(new InsnNode(148));
                        insnList.add(new JumpInsnNode(154, L_end1stCheck));
                        this.insnListAddReturn(method, insnList);
                        insnList.add(L_end1stCheck);
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new JumpInsnNode(198, L_continue));
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new TypeInsnNode(193, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                        insnList.add(new JumpInsnNode(153, L_isInstance));
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new TypeInsnNode(192, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                        insnList.add(new JumpInsnNode(167, L_endif));
                        insnList.add(L_isInstance);
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                false
                            )
                        );
                        insnList.add(L_endif);
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );

                        for (String className : this.mClassNameStartsOfProtectedFieldsGet) {
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_popAndReturnAuto));
                        }

                        insnList.add(new InsnNode(87));
                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_popAndReturnAuto);
                        insnList.add(new InsnNode(87));
                        this.insnListAddReturn(method, insnList);
                        insnList.add(L_PopAndContinue);
                        insnList.add(new InsnNode(87));
                        insnList.add(L_continue);
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var13) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_dllLoad(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode mInsn = (MethodInsnNode)insn;
                        boolean isDllLoad = false;
                        boolean returnsInterface = false;
                        if (mInsn.getOpcode() == 184
                            && mInsn.owner.equals(MyCheck.C.dec("ljMyRMt6rWKiAwEz3xo1LWba960x3fI/jwdH2F9Ns4c="))
                            && (mInsn.name.equals(MyCheck.C.dec("2XTVPY2kByiky5q1eiymug==")) || mInsn.name.equals(MyCheck.C.dec("SXr1MQVmO8n2N+Qd6yQ8Hw==")))) {
                            isDllLoad = true;
                        }

                        if (mInsn.getOpcode() == 182
                            && mInsn.owner.equals(MyCheck.C.dec("4/hH/QUHKzkHdH3n8T05jWxt0+pBXMELLAW7prSRCZU="))
                            && (mInsn.name.equals(MyCheck.C.dec("2XTVPY2kByiky5q1eiymug==")) || mInsn.name.equals(MyCheck.C.dec("SXr1MQVmO8n2N+Qd6yQ8Hw==")))) {
                            isDllLoad = true;
                        }

                        if (mInsn.getOpcode() == 184
                            && mInsn.owner.equals(MyCheck.C.dec("ZKRswVsxhf1QM3jbhWiMOs7NT5M7qPXehyhLQZ8+hdw="))
                            && (
                                mInsn.name.equals(MyCheck.C.dec("2XTVPY2kByiky5q1eiymug=="))
                                    || mInsn.name.equals(MyCheck.C.dec("SXr1MQVmO8n2N+Qd6yQ8Hw=="))
                                    || mInsn.name.equals(MyCheck.C.dec("xhsgJTUjCP37ScZFmwfoYg=="))
                            )) {
                            isDllLoad = true;
                            if (Type.getReturnType(mInsn.desc).getSort() == 10) {
                                returnsInterface = true;
                            }
                        }

                        if (isDllLoad) {
                            InsnList insnList = new InsnList();
                            Type[] args = Type.getArgumentTypes(mInsn.desc);

                            for (int i = args.length - 1; i >= 0; i--) {
                                int sort = args[i].getSort();
                                if (sort != 7 && sort != 8) {
                                    insnList.add(new InsnNode(87));
                                } else {
                                    insnList.add(new InsnNode(88));
                                }
                            }

                            if (mInsn.getOpcode() != 184) {
                                insnList.add(new InsnNode(87));
                            }

                            if (returnsInterface && mInsn.owner.equals(MyCheck.C.dec("ZKRswVsxhf1QM3jbhWiMOs7NT5M7qPXehyhLQZ8+hdw="))) {
                                String ifaceInternalName = null;
                                boolean nextHasCheckcast = false;
                                AbstractInsnNode next = insn.getNext();

                                while (next != null && (next.getType() == 8 || next.getType() == 15 || next.getType() == 14)) {
                                    next = next.getNext();
                                }

                                if (next instanceof TypeInsnNode) {
                                    TypeInsnNode typeInsnNode = (TypeInsnNode)next;
                                    if (typeInsnNode.getOpcode() == 192) {
                                        ifaceInternalName = typeInsnNode.desc;
                                        nextHasCheckcast = true;
                                    }
                                }

                                if (ifaceInternalName == null) {
                                    for (AbstractInsnNode prev = insn.getPrevious(); prev != null; prev = prev.getPrevious()) {
                                        if (prev instanceof LdcInsnNode ldcInsnNode
                                            && ldcInsnNode.cst instanceof Type type
                                            && (type.getSort() == 10 || type.getSort() == 9)) {
                                            ifaceInternalName = type.getInternalName();
                                            break;
                                        }
                                    }
                                }

                                if (ifaceInternalName != null) {
                                    insnList.add(
                                        new LdcInsnNode(
                                            Type.getType(
                                                MyCheck.C.dec("AnrByDJds/he1kwdcU1XLQ==") + ifaceInternalName + MyCheck.C.dec("U0bJu972ae61PbdUXZDGAg==")
                                            )
                                        )
                                    );
                                    insnList.add(
                                        new MethodInsnNode(
                                            184,
                                            MyCheck.C.dec("ai7EaHeYu72UwqWo3zwk6Q=="),
                                            MyCheck.C.dec("WEl7vLiZJkkhCIvKQOMbu2ba960x3fI/jwdH2F9Ns4c="),
                                            MyCheck.C.dec("ZGfSxE0vJdk3ybd3zC9mVdSgMteGDNAAv7VnsRGOfB1RDdQJ/A68AYYoambSVOjq"),
                                            false
                                        )
                                    );
                                    if (!nextHasCheckcast) {
                                        insnList.add(new TypeInsnNode(192, ifaceInternalName));
                                    }
                                } else {
                                    insnList.add(new InsnNode(1));
                                }
                            } else {
                                this.insnListAdd0null(mInsn.desc, insnList);
                            }

                            method.instructions.insertBefore(insn, insnList);
                            method.instructions.remove(insn);
                            bChanged = true;
                        }
                    }
                }
            }
        } catch (Throwable var20) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_dllString(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode)insn;
                        String lowerString = (String)ldc.cst;
                        if (lowerString instanceof String) {
                            String string = lowerString;
                            lowerString = string.toLowerCase(Locale.ROOT);
                            if (lowerString.endsWith(MyCheck.C.dec("eu7hEi8lrQULLpQ2qadBHA=="))) {
                                String newString = string.replaceAll(MyCheck.C.dec("Y18Tu/l10aVN2/IHerFh+w=="), MyCheck.C.dec("xG5kP1n1zzUvaNYBak6qiA=="));
                                ldc.cst = newString;
                                bChanged = true;
                            }
                        }
                    }
                }
            }
        } catch (Throwable var13) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_dllCall(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                boolean canMakeEmpty = this.canMakeMethodEmpty(method);
                boolean callingOSAPI = false;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode mInsn = (MethodInsnNode)insn;
                        if ((mInsn.getOpcode() == 185 || mInsn.getOpcode() == 182)
                            && mInsn.owner.startsWith(MyCheck.C.dec("aSTESE3Q9oMXe4AOIgkt+7nsJeBG1p2JnSNj3AWmPME="))) {
                            callingOSAPI = true;
                            if (canMakeEmpty) {
                                break;
                            }
                        }
                    }
                }

                if (callingOSAPI && this.canMakeMethodEmpty(method)) {
                    this.makeMethodEmpty(classNode, method);
                    bChanged = true;
                }
            }
        } catch (Throwable var12) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_nativeMethod(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                if ((method.access & 256) != 0) {
                    method.access &= -257;
                    method.instructions.clear();
                    method.tryCatchBlocks.clear();
                    method.localVariables = null;
                    InsnList insnList = new InsnList();
                    Type returnType = Type.getReturnType(method.desc);
                    switch (returnType.getSort()) {
                        case 0:
                            insnList.add(new InsnNode(177));
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            insnList.add(new InsnNode(3));
                            insnList.add(new InsnNode(172));
                            break;
                        case 6:
                            insnList.add(new InsnNode(11));
                            insnList.add(new InsnNode(174));
                            break;
                        case 7:
                            insnList.add(new InsnNode(9));
                            insnList.add(new InsnNode(173));
                            break;
                        case 8:
                            insnList.add(new InsnNode(14));
                            insnList.add(new InsnNode(175));
                            break;
                        case 9:
                            insnList.add(new InsnNode(1));
                            insnList.add(new InsnNode(176));
                            break;
                        case 10:
                            insnList.add(
                                new LdcInsnNode(
                                    Type.getType(
                                        MyCheck.C.dec("AnrByDJds/he1kwdcU1XLQ==") + returnType.getInternalName() + MyCheck.C.dec("U0bJu972ae61PbdUXZDGAg==")
                                    )
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("ai7EaHeYu72UwqWo3zwk6Q=="),
                                    MyCheck.C.dec("WEl7vLiZJkkhCIvKQOMbu2ba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("ZGfSxE0vJdk3ybd3zC9mVdSgMteGDNAAv7VnsRGOfB1RDdQJ/A68AYYoambSVOjq"),
                                    false
                                )
                            );
                            insnList.add(new InsnNode(176));
                    }

                    method.instructions.insert(insnList);
                    bChanged = true;
                }
            }
        } catch (Throwable var7) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_ifWin(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode mInsn = (MethodInsnNode)insn;
                        if (mInsn.getOpcode() == 184
                            && MyCheck.C.dec("ljMyRMt6rWKiAwEz3xo1LWba960x3fI/jwdH2F9Ns4c=").equals(mInsn.owner)
                            && MyCheck.C.dec("Oh6RiPKf8yttXOHpkNYGww==").equals(mInsn.name)
                            && (
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52").equals(mInsn.desc)
                                    || MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMDP1QKoaTPbOCCE7xabeIzMr0wPz5zz2uSeIcOueCT7tJisQ+LVKwWGONhYT1soxoQ==")
                                        .equals(mInsn.desc)
                            )) {
                            AbstractInsnNode twoArgs = mInsn.getPrevious();
                            if (twoArgs instanceof LdcInsnNode) {
                                LdcInsnNode ldc = (LdcInsnNode)twoArgs;
                                if (MyCheck.C.dec("VRJpa9FCZ7Mexkjy5OxL2g==").equals(ldc.cst)) {
                                    boolean twoArgsx = MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMDP1QKoaTPbOCCE7xabeIzMr0wPz5zz2uSeIcOueCT7tJisQ+LVKwWGONhYT1soxoQ==")
                                        .equals(mInsn.desc);
                                    InsnList insnList = new InsnList();
                                    if (twoArgsx) {
                                        insnList.add(new InsnNode(87));
                                    }

                                    insnList.add(new InsnNode(87));
                                    insnList.add(new LdcInsnNode(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==")));
                                    method.instructions.insert(mInsn, insnList);
                                    method.instructions.remove(mInsn);
                                    bChanged = true;
                                }
                            }
                        }
                    } else if (insn instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode)insn;
                        if (ldc.cst instanceof String) {
                            String cst = (String)ldc.cst;
                            if (cst.equals(MyCheck.C.dec("VRJpa9FCZ7Mexkjy5OxL2g=="))) {
                                InsnList insnList = new InsnList();
                                insnList.add(new LdcInsnNode(MyCheck.C.dec("pASAi81FI8hrOWjqIKtjAQ==")));
                                method.instructions.insert(ldc, insnList);
                                method.instructions.remove(ldc);
                                System.setProperty(MyCheck.C.dec("pASAi81FI8hrOWjqIKtjAQ=="), MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="));
                                bChanged = true;
                            }
                        }
                    }
                }
            }
        } catch (Throwable var15) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_methodHandle(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                InsnList insns = method.instructions;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode methodInsn = (MethodInsnNode)insn;
                        if (methodInsn.getOpcode() == 182
                            && methodInsn.owner.equals(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"))
                            && methodInsn.name.startsWith(MyCheck.C.dec("WBuxMJjne9eyVcpvp7VCOA=="))
                            && !methodInsn.name.equals(MyCheck.C.dec("2TjO56qxWOL9iPtOA+SLXA=="))
                            && methodInsn.desc.endsWith(MyCheck.C.dec("i4bIhZH88mcbPjqfMpx1xTVkUwisowjlilkltEQPNXFm2vetMd3yP48HR9hfTbOH"))) {
                            InsnList inject = new InsnList();
                            inject.add(
                                new LdcInsnNode(
                                    MyCheck.C.dec("EoXK2Pe24e92HuP/HOeEdWba960x3fI/jwdH2F9Ns4c=")
                                        + method.name
                                        + MyCheck.C.dec("xiDHWDbeERn7LRjDqgD9OLtevmIS+u+UT2k3FAQH/nQ=")
                                )
                            );
                            inject.add(
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("8iVoJ5fhMV8nGSibbTJn1WdQY67lsRGwd/p4S0+wDqXaaW9cHndXAiM/bmvFTeah"),
                                    MyCheck.C.dec("aZyDEitBIuZxros18CzY6kiU3C22n+aHPE/qzoJ89jY="),
                                    MyCheck.C.dec(
                                        "UceTux3RViKBlhoyFYAy6TVkUwisowjlilkltEQPNXFEOO67wFU+OMVp6+QAM2S0k9O2t6r76qmffHpUkNdYhfOH4mAq7vBKtKo+MO/h1DhGzv23JjzX9II7IO5oZfhM"
                                    ),
                                    false
                                )
                            );
                            insns.insert(insn, inject);
                            bChanged = true;
                        }
                    }
                }
            }
        } catch (Throwable var12) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_varHandle(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                InsnList insns = method.instructions;

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode methodInsn = (MethodInsnNode)insn;
                        if (methodInsn.owner.equals(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/mO4lgo7cxCKh0Y+xfyfpNo="))
                            && (
                                methodInsn.name.startsWith(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw=="))
                                    || methodInsn.name.contains(MyCheck.C.dec("Rppu7xgLgTxcweAj6b3A1A=="))
                            )) {
                            if (!methodInsn.name.equals(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw=="))
                                || !methodInsn.desc.equals(MyCheck.C.dec("AEUPCGBbqQLtfI3e2iLUWQ=="))) {
                                this.makeMethodCallEmpty(method, methodInsn);
                                bChanged = true;
                            }
                        } else if (methodInsn.getOpcode() == 182
                            && MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/mO4lgo7cxCKh0Y+xfyfpNo=").equals(methodInsn.owner)
                            && MyCheck.C.dec("lC9LiWfAi35+jLXEWKO3/A==").equals(methodInsn.name)
                            && (
                                methodInsn.desc.equals(MyCheck.C.dec("ZGfSxE0vJdk3ybd3zC9mVdSgMteGDNAAv7VnsRGOfB1RDdQJ/A68AYYoambSVOjq"))
                                    || methodInsn.desc.equals(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlvIGfOjwy0P1iF8crMVpB+Oq57akEA1Ezl1I3lj5sVMH"))
                            )) {
                            insns.set(
                                methodInsn,
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("8iVoJ5fhMV8nGSibbTJn1UzC2eyQTVHsZFfGbOyVdek="),
                                    MyCheck.C.dec("CdDSp+2oJonf0IrmROLXCQ=="),
                                    MyCheck.C.dec(
                                        "UceTux3RViKBlhoyFYAy6YK23kuVc1yBi6UmwR1G10l2wfOz3/ImKGOEiGye0BkYbGtkI5cOW5E4pw7vw+1i5vZIXC+iboQ1qykOR2U85nE="
                                    ),
                                    false
                                )
                            );
                            bChanged = true;
                        }
                    }
                }
            }
        } catch (Throwable var11) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    public static Object myVarHandleGet(VarHandle varHandle, Object arg1) {
        return varHandle.toString().contains(MyCheck.C.dec("SSg69TV0XqispkkZUExoLg==")) && arg1 instanceof Class<?> clazz
            ? gMapClassClassData.getOrDefault(clazz, null)
            : (Object)varHandle.get((Object)arg1);
    }

    private boolean tranNodes_badLoop(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                for (AbstractInsnNode insn : method.instructions) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode methodInsn = (MethodInsnNode)insn;
                        if (methodInsn.owner.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6TfNhTyvzHvAri6iD2cx+FA="))
                            && methodInsn.name.equals(MyCheck.C.dec("C/a4+wgD/5uDWtUeniDp9w=="))
                            && methodInsn.desc.equals(MyCheck.C.dec("echQ/pNkYm3m0oxudd2+tw=="))) {
                            AbstractInsnNode next = methodInsn.getNext();
                            if (next instanceof JumpInsnNode) {
                                JumpInsnNode jump = (JumpInsnNode)next;
                                if ((jump.getOpcode() == 153 || jump.getOpcode() == 154) && this.hasBackJumpTo(methodInsn, jump)) {
                                    AbstractInsnNode prev = methodInsn.getPrevious();
                                    if (prev != null && (prev.getOpcode() == 25 || prev.getOpcode() == 178 || prev.getOpcode() == 184)) {
                                        method.instructions.remove(prev);
                                    }

                                    InsnList insnList = new InsnList();
                                    insnList.add(new InsnNode(3));
                                    method.instructions.insert(methodInsn, insnList);
                                    method.instructions.remove(methodInsn);
                                    bChanged = true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable var12) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean hasBackJumpTo(MethodInsnNode condCall, JumpInsnNode condJump) {
        LabelNode startLabel = null;
        AbstractInsnNode cur = condCall.getPrevious();

        for (; cur != null; cur = cur.getPrevious()) {
            if (cur instanceof LabelNode) {
                startLabel = (LabelNode)cur;
                break;
            }
        }

        if (startLabel == null) {
            return false;
        } else {
            for (AbstractInsnNode scan = condCall.getNext(); scan != null && scan != condJump.label; scan = scan.getNext()) {
                if (scan instanceof JumpInsnNode j && j.getOpcode() == 167 && j.label == startLabel) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean tranNodes_osCommand(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                AbstractInsnNode[] var5 = method.instructions.toArray();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; var7++) {
                    if (var5[var7] instanceof MethodInsnNode methodInsn
                        && (
                            methodInsn.owner.equals(MyCheck.C.dec("4/hH/QUHKzkHdH3n8T05jWxt0+pBXMELLAW7prSRCZU="))
                                    && methodInsn.name.equals(MyCheck.C.dec("FOfQnurNjws9V/Cg0GpIkA=="))
                                || methodInsn.owner.equals(MyCheck.C.dec("Sa/M3xaPv+mB32jxOOTB28XYLuhyN1/pDiUXpfrUZ18="))
                                    && methodInsn.name.equals(MyCheck.C.dec("exlnk25LIl7tubAzQSLJPg=="))
                                || methodInsn.owner.equals(MyCheck.C.dec("os140XCvhR5g2Hg6i0yn9Gba960x3fI/jwdH2F9Ns4c="))
                                    && (
                                        methodInsn.name.equals(MyCheck.C.dec("EDSSYhe3BmaRLyUu5EEfHg=="))
                                            || methodInsn.name.equals(MyCheck.C.dec("YPcfg3dhqiCuxOVqUKM9Tg=="))
                                    )
                                || methodInsn.owner.endsWith(MyCheck.C.dec("6Nhxn9AsIuR8Z73Laud02w=="))
                                || methodInsn.owner.endsWith(MyCheck.C.dec("rpFf7NtEOcwZRDqTildQ6g=="))
                        )) {
                        if (this.canMakeMethodEmpty(method)) {
                            this.makeMethodEmpty(classNode, method);
                            bChanged = true;
                            break;
                        }

                        this.makeMethodCallEmpty(method, methodInsn);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var10) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_vmAttach(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
                return false;
            }

            for (MethodNode method : classNode.methods) {
                AbstractInsnNode[] var5 = method.instructions.toArray();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; var7++) {
                    if (var5[var7] instanceof MethodInsnNode methodInsn
                        && methodInsn.owner.equals(MyCheck.C.dec("yf/mpISzCjSS7EaSfTZTeYO8Uy+wuQe1NUGhUjtFcpeRTkYjZXop7GuT3WCY32RP"))
                        && methodInsn.name.equals(MyCheck.C.dec("eRJbNHDLgljX8KZ1wJ4gOw=="))) {
                        if (this.canMakeMethodEmpty(method)) {
                            this.makeMethodEmpty(classNode, method);
                            bChanged = true;
                            break;
                        }

                        this.makeMethodCallEmpty(method, methodInsn);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var10) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_fixVanilla(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("uyAGxCa1dxC9Xz7XtByKB+0ZEvZLz5i3EhRCQ7bZpVAHABSdnfmB2t9MeoMFwH/E"))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("d+0KueTGDEG+vXcATNKFnQ=="))) {
                        for (AbstractInsnNode insn : method.instructions.toArray()) {
                            if (insn instanceof MethodInsnNode) {
                                MethodInsnNode mInsn = (MethodInsnNode)insn;
                                if (mInsn.owner.equals(MyCheck.C.dec("uyAGxCa1dxC9Xz7XtByKB2aHq86F3c33vV+Jd9mskCU5ZxXrVMi9qCiIUPVCX08S"))
                                    && mInsn.name.equals(MyCheck.C.dec("v/BQUWEox6oB+Iz8nCLKGw=="))
                                    && mInsn.desc.equals(MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMN7U5AxLXqNvoOj3M0fGfxE/B77r2By0TFXGuA9/d0jA"))) {
                                    method.instructions.insertBefore(mInsn, new InsnNode(87));
                                    mInsn.desc = MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMIiKLf/BYpYHFdfYVvffY8Q=");
                                    bChanged = true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable var10) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_reflectionCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("oobS1MQOVhDY3bJVjXV/0w=="))) {
                        InsnList insnList = new InsnList();
                        LabelNode L_skipUnsafeByMethod = new LabelNode();
                        LabelNode L_skipFieldByMethod = new LabelNode();
                        LabelNode L_skipMethodByMethod = new LabelNode();
                        LabelNode L_skipVarHandleByMethod = new LabelNode();
                        LabelNode L_skipMethodHandleByMethod = new LabelNode();
                        LabelNode L_continue = new LabelNode();
                        LabelNode L_returnAuto = new LabelNode();
                        LabelNode L_returnVoidOrNull = new LabelNode();
                        LabelNode L_returnTrue = new LabelNode();
                        LabelNode L_returnInt = new LabelNode();
                        LabelNode L_returnLong = new LabelNode();
                        LabelNode L_returnByte = new LabelNode();
                        LabelNode L_returnShort = new LabelNode();
                        LabelNode L_returnChar = new LabelNode();
                        LabelNode L_returnFloat = new LabelNode();
                        LabelNode L_returnDouble = new LabelNode();
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("tuosqwLErhk6M1gir2GSVQ==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(153, L_skipUnsafeByMethod));

                        for (String listType : List.of(
                            MyCheck.C.dec("tpCS+CB+fuj7tsqR0CmELg=="),
                            MyCheck.C.dec("pqR2b1Fv793PFdl+7Lk2dQ=="),
                            MyCheck.C.dec("IBFxgAq/uH/6xjsUAQ/zUA=="),
                            MyCheck.C.dec("r5B9/dUNv114hoXnzRaN5w=="),
                            MyCheck.C.dec("/I5VsUmreHQEbct2mV/q3A=="),
                            MyCheck.C.dec("ALENrtV1+yW070096Ifozw=="),
                            MyCheck.C.dec("+xC57TG9reIkiOQznKyuVQ=="),
                            MyCheck.C.dec("g6qUQoeGGdE56uTUdYUswA=="),
                            MyCheck.C.dec("Y8JtNXGTc/IEsh5WxCjsbA=="),
                            MyCheck.C.dec("FAR5S6DOCFMpyTcpCJ1Kxg==")
                        )) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                    MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                    MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                                )
                            );
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("nmjf/q4+4Mtn4kOKo+7vYA==") + listType));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnAuto));
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                    MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                    MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                                )
                            );
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("uXUfdEb4vHwbgiKB0fL96A==") + listType));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnAuto));
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                    MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                    MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                                )
                            );
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("Rppu7xgLgTxcweAj6b3A1A==") + listType));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                    MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnAuto));
                        }

                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_skipUnsafeByMethod);
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("BfWEKzUwQGn/czMS13LTZfxQJSz61X3xAb4GWzMhepM=")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(153, L_skipFieldByMethod));

                        for (String listType : List.of(
                            MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw=="),
                            MyCheck.C.dec("tpCS+CB+fuj7tsqR0CmELg=="),
                            MyCheck.C.dec("r5B9/dUNv114hoXnzRaN5w=="),
                            MyCheck.C.dec("/I5VsUmreHQEbct2mV/q3A=="),
                            MyCheck.C.dec("ALENrtV1+yW070096Ifozw=="),
                            MyCheck.C.dec("+xC57TG9reIkiOQznKyuVQ=="),
                            MyCheck.C.dec("g6qUQoeGGdE56uTUdYUswA=="),
                            MyCheck.C.dec("Y8JtNXGTc/IEsh5WxCjsbA=="),
                            MyCheck.C.dec("FAR5S6DOCFMpyTcpCJ1Kxg==")
                        )) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                    MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                    MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                                )
                            );
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw==") + listType));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                    MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnAuto));
                        }

                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_skipFieldByMethod);
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("BfWEKzUwQGn/czMS13LTZbbBYZ4Mp2CRonWO8XNuKls=")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(153, L_skipMethodByMethod));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("oobS1MQOVhDY3bJVjXV/0w==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_returnAuto));
                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_skipMethodByMethod);
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsLqncoGEq3P55R22yKLCsEM=")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(153, L_skipVarHandleByMethod));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_returnAuto));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("Rppu7xgLgTxcweAj6b3A1A==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_returnAuto));
                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_skipVarHandleByMethod);
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("3O0qTn0EB+EFDjGPz4HNsDXFsSgKaPj5b/ELrkdu1C4=")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(153, L_skipMethodHandleByMethod));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("oobS1MQOVhDY3bJVjXV/0w==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_returnAuto));
                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_skipMethodHandleByMethod);

                        for (String badCall : this.mBadCalleeNames) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                    MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                    MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")));
                            this.insnListAdd_String_concat(insnList);
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                    MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="),
                                    MyCheck.C.dec("RDjuu8BVPjjFaevkADNktCKi9xx+Xkro3s5zyHk/+KU=")
                                )
                            );
                            this.insnListAdd_String_concat(insnList);
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==")));
                            this.insnListAdd_String_concat(insnList);
                            insnList.add(new LdcInsnNode(badCall));
                            this.insnListAdd_String_startsWith_thenJump(insnList, L_returnAuto);
                        }

                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_returnAuto);

                        for (String whiteCaller : List.of(
                            MyCheck.C.dec("SSg69TV0XqispkkZUExoLg=="),
                            MyCheck.C.dec("d8m67omzvTrM5GTNc7MyApmvVTPd8flOC3gLmwcX/lg="),
                            MyCheck.C.dec("79pneHF1w69th+nfmt8iQg==")
                        )) {
                            insnList.add(
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("jrHaJ9oZGVE1wo/7fqUqd6ng6p5O9/TDadZgEOl53X8="),
                                    MyCheck.C.dec("nEI30fKfWEdkx431MejL6g=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(whiteCaller));
                            this.insnListAdd_String_startsWith_thenJump(insnList, L_continue);
                        }

                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("mGbOUy8IsYCVBgenR7w+beVawrnsrSeItfGJi1ImlJ0="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnTrue));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("AjoQi9YyyCj483Lk8ui3ZRwwQQcskbrk+MSvszS6ZIM="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnInt));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("yoETgnpRaG3Noixq/xYDfw=="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnLong));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("VMDPhlRVL2Q3Do7O0f5NlQ=="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnByte));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("P0dwsYhgsQLp8/2nRfr9+g=="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnShort));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("WdIwNxoYXbcLCiO08LPs3AlN7dBiTndDdBxf3l9/+tY="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnChar));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("aqTvH9elJ+mAiOwMsQL3FA=="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnFloat));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2ohP6twMucBJWN0yzK8Ee8Q="),
                                MyCheck.C.dec("Ev9x6XbSEs8OxWNJJnw1JA=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("Y3hqhiRmBfkD1gaddOBWKmba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("RCIy5dDFdvMkig2rdxH9GQ=="),
                                MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                            )
                        );
                        insnList.add(new JumpInsnNode(165, L_returnDouble));
                        insnList.add(new JumpInsnNode(167, L_returnVoidOrNull));
                        insnList.add(L_returnVoidOrNull);
                        insnList.add(new InsnNode(1));
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnTrue);
                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("mGbOUy8IsYCVBgenR7w+beVawrnsrSeItfGJi1ImlJ0="),
                                MyCheck.C.dec("Lb5ZwlW0BZDElKRTusBwFg=="),
                                MyCheck.C.dec("ysBifbE7bm9ujV66Tg6Zke1iQOEcV2PnMHDHmGtfx+I=")
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnInt);
                        insnList.add(new InsnNode(3));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("AjoQi9YyyCj483Lk8ui3ZRwwQQcskbrk+MSvszS6ZIM="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("OkdnvAnkP3jnLGuPqPOyzNQ+Ap/QYZFfbrvxyY3Ha0E="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnLong);
                        insnList.add(new InsnNode(9));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("yoETgnpRaG3Noixq/xYDfw=="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("6TnQf2aRVvApsdwgbPhS62HDgX7Ojt00/aKvriv0xmQ="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnByte);
                        insnList.add(new InsnNode(3));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("VMDPhlRVL2Q3Do7O0f5NlQ=="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("I9g0bZZjtORrSqEqg0kvM31uboTzaVUjqMzHISDTfLQ="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnShort);
                        insnList.add(new InsnNode(3));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("P0dwsYhgsQLp8/2nRfr9+g=="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("dlcxm0lpNtedgNkAdVcQwbVx17cs8WkoozeYiStF3YM="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnChar);
                        insnList.add(new InsnNode(3));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("WdIwNxoYXbcLCiO08LPs3AlN7dBiTndDdBxf3l9/+tY="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("Hb8omhrAya5RpF/jHsBPPuSS8r2UkSnmcFi/XdazvF0="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnFloat);
                        insnList.add(new InsnNode(11));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("aqTvH9elJ+mAiOwMsQL3FA=="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("96kjpooxlJYypkdE/kT3X1j5+2p3WWtEnFHMOKZRqB4="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_returnDouble);
                        insnList.add(new InsnNode(14));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("Y3hqhiRmBfkD1gaddOBWKmba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("MDGHn9Bll4sCowMv3I8S9Q=="),
                                MyCheck.C.dec("SSG9kcrBvuDbeD0KNqxV3ai58F9ZeDGOXmqM6vQzlwU="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_continue);
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            } else if (classNode.name.startsWith(MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2sz0TUQk2uJVWj98NJqC0OE="))) {
                for (MethodNode methodx : classNode.methods) {
                    if (methodx.name.startsWith(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw=="))
                        && methodx.desc.startsWith(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlgAKord5nE2+DxUve1cBYys="))
                        && methodx.desc.endsWith(MyCheck.C.dec("j08rxPCRW2PthUhVmHjHkg=="))) {
                        InsnList insnList = new InsnList();
                        LabelNode L_returnVoid = new LabelNode();
                        LabelNode L_continue = new LabelNode();

                        for (String className : List.of(
                            MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJ++A0G9xnU5XW/0PEccjOTTIAq40ZwI0OS0SANqwxFA7"),
                            MyCheck.C.dec("78UjT7MZooo0yVpH9+9azIH96rEWW3RygxKJirXst/A="),
                            MyCheck.C.dec("pGd7vi7MFxWEWJd2lxnpe3fMCnFszASr4xn8FRFzfJyfsq45laHEKXAs1yAaOgK8ozF/WJ+1Kc503ItdYfTyzhwwQQcskbrk+MSvszS6ZIM="),
                            MyCheck.C.dec("8AjI6lEU7i9/ae2sfEAtQutrGPHLHqz1o9kddo3sAIaWrMBjMN5lzH3RLhjEKkrMXXVo1bugSFVKfBVVWMSmag=="),
                            MyCheck.C.dec("d8m67omzvTrM5GTNc7MyApmvVTPd8flOC3gLmwcX/lg="),
                            MyCheck.C.dec("79pneHF1w69th+nfmt8iQg==")
                        )) {
                            insnList.add(
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("jrHaJ9oZGVE1wo/7fqUqd6ng6p5O9/TDadZgEOl53X8="),
                                    MyCheck.C.dec("nEI30fKfWEdkx431MejL6g=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_continue));
                        }

                        for (String className : this.mFullClassNamesOfProtectedFieldsSet) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2sz0TUQk2uJVWj98NJqC0OE="),
                                    MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                    MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                    MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnVoid));
                        }

                        for (String className : this.mClassNameStartsOfProtectedFieldsSet) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2sz0TUQk2uJVWj98NJqC0OE="),
                                    MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                    MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnVoid));
                        }

                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_returnVoid);
                        insnList.add(new InsnNode(1));
                        insnList.add(new InsnNode(176));
                        insnList.add(L_continue);
                        methodx.instructions.insert(insnList);
                        bChanged = true;
                    }

                    if (methodx.name.equals(MyCheck.C.dec("lC9LiWfAi35+jLXEWKO3/A=="))
                        && methodx.desc.equals(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlvIGfOjwy0P1iF8crMVpB+Oq57akEA1Ezl1I3lj5sVMH"))) {
                        InsnList insnList = new InsnList();
                        LabelNode L_returnNull = new LabelNode();
                        LabelNode L_continue = new LabelNode();

                        for (String className : List.of(
                            MyCheck.C.dec("d8m67omzvTrM5GTNc7MyApmvVTPd8flOC3gLmwcX/lg="), MyCheck.C.dec("79pneHF1w69th+nfmt8iQg==")
                        )) {
                            insnList.add(
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("jrHaJ9oZGVE1wo/7fqUqd6ng6p5O9/TDadZgEOl53X8="),
                                    MyCheck.C.dec("nEI30fKfWEdkx431MejL6g=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_continue));
                        }

                        for (String className : this.mClassNameStartsOfProtectedFieldsGet) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    MyCheck.C.dec("QHxX/zwpeUsk87ewkX5L2sz0TUQk2uJVWj98NJqC0OE="),
                                    MyCheck.C.dec("co+XSVeWSBj0STVl1PX0nA=="),
                                    MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnNull));
                        }

                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_returnNull);
                        this.insnListAddReturn(methodx, insnList);
                        insnList.add(L_continue);
                        methodx.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var27) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_varHandleCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/mO4lgo7cxCKh0Y+xfyfpNo="))
                && (
                    classNode.name.contains(MyCheck.C.dec("k1WjzFOAT70LrZugQupmuROg4lMx3NqC8a9b7DA5PLQ="))
                        || classNode.name.contains(MyCheck.C.dec("nLvi5uT9PJIHzQUI+97grd7iqLjF2NV8IG1UQrkbmQk="))
                )) {
                for (MethodNode method : classNode.methods) {
                    boolean bIsInstance = classNode.name.contains(MyCheck.C.dec("k1WjzFOAT70LrZugQupmuROg4lMx3NqC8a9b7DA5PLQ="));
                    if ((
                            bIsInstance && method.desc.startsWith(MyCheck.C.dec("UceTux3RViKBlhoyFYAy6YK23kuVc1yBi6UmwR1G10kulfj3iWEeObPt/VT/n3OH"))
                                || !bIsInstance && method.desc.startsWith(MyCheck.C.dec("UceTux3RViKBlhoyFYAy6QjNJUObYBia0u3/ZaYDENU="))
                        )
                        && (
                            method.name.startsWith(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw=="))
                                || method.name.contains(MyCheck.C.dec("O3mTHuQChe+K40h+fHufaA=="))
                                || method.name.startsWith(MyCheck.C.dec("/F3iv+cz0Te3E0lR3iuUXw=="))
                        )) {
                        InsnList insnList = new InsnList();
                        LabelNode L_returnAuto = new LabelNode();
                        LabelNode L_continue = new LabelNode();
                        LabelNode L_PopAndContinue = new LabelNode();
                        if (bIsInstance) {
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(new JumpInsnNode(198, L_continue));
                            insnList.add(new VarInsnNode(25, 1));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                    false
                                )
                            );
                        } else {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    classNode.name.replace(MyCheck.C.dec("eYXnMG3JIyS0Ek6hnAGZbw=="), MyCheck.C.dec("Rrxy4OsUghgJzqAhmGbBrA==")),
                                    MyCheck.C.dec("fbzdA/Bd72esdWvAaEmZsQ=="),
                                    MyCheck.C.dec("bGtkI5cOW5E4pw7vw+1i5vZIXC+iboQ1qykOR2U85nE=")
                                )
                            );
                            insnList.add(new TypeInsnNode(192, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                        }

                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );

                        for (String className : List.of(
                            MyCheck.C.dec("p4bgRrQdUhK5yir766SA/jGx/qxq0Cr9uBMYz/1cLd8="),
                            MyCheck.C.dec("+ZsTJLLm4ArNUrMNHbGrhTJ+frPRvCp9pJ6DD8ontXQ="),
                            MyCheck.C.dec("0UF+Q6ACv83aW9lIGBZDVw=="),
                            MyCheck.C.dec("ChaaRfahKB76TkE9eMzTrA=="),
                            MyCheck.C.dec("qAKUP5QcrkIjpjWTTy0AWm6nc0+YwGLAwDlJnpnYbJE="),
                            MyCheck.C.dec("Va4g9aig2lYOf3euoI88Wg=="),
                            MyCheck.C.dec("PWZ2W2uWswoba83CiF+Jkhh76s29ZzSK1s+tVdfRIQY="),
                            MyCheck.C.dec("cuvQVgR4mRTJt3Yq/GZHz6Xfe5MaVQSqTGWY+0FKKfU=")
                        )) {
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_PopAndContinue));
                        }

                        if (bIsInstance && method.name.equals(MyCheck.C.dec("Fh9M+tA8IFhPp9gwgp+ZSw=="))) {
                            insnList.add(
                                new MethodInsnNode(
                                    184,
                                    MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("fdzbK6qUW+78VdHx2tK/FQ=="),
                                    MyCheck.C.dec("Z0dHxxcJPly7JmcSCpnwe2O7mBIjcbDFHs89DfCagV0="),
                                    false
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("FJA7d/gCp0ZfOUgZr+4kcw=="),
                                    MyCheck.C.dec("Xl8Hb2GG0zOWXTbAJ+ocy5yhXVuySZbfR7+ae2Qj/ARm2vetMd3yP48HR9hfTbOH"),
                                    false
                                )
                            );
                            insnList.add(new InsnNode(5));
                            insnList.add(new InsnNode(50));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                                    MyCheck.C.dec("zFL1G6W6qIQGKn7NKhLfaw=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(MyCheck.C.dec("p4bgRrQdUhK5yir766SA/vq7K56b++/+7hGhVQAipww=")));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_PopAndContinue));
                        }

                        for (String className : this.mFullClassNamesOfProtectedFieldsSet) {
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                    MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnAuto));
                        }

                        for (String className : this.mClassNameStartsOfProtectedFieldsSet) {
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnAuto));
                        }

                        insnList.add(new InsnNode(87));
                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_returnAuto);
                        insnList.add(new InsnNode(87));
                        if (method.desc.endsWith(MyCheck.C.dec("d/Na+OSfI7Lv+JitPp4oiQ=="))) {
                            insnList.add(new InsnNode(4));
                        } else {
                            this.insnListAdd0null(method.desc, insnList);
                        }

                        insnList.add(new InsnNode(Type.getReturnType(method.desc).getOpcode(172)));
                        insnList.add(L_PopAndContinue);
                        insnList.add(new InsnNode(87));
                        insnList.add(L_continue);
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            } else if (classNode.name.startsWith(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/uZt3Mka+adc7jK2+qkpGgtwBC8uR8mMrrNv41g662R6kg7bdYQUqDBc1PBxkRqFsg=="))
                || classNode.name.startsWith(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/uZt3Mka+adc7jK2+qkpGguDxZfhQ9T70/cej7r2/1k+Rrxy4OsUghgJzqAhmGbBrA=="))) {
                for (MethodNode methodx : classNode.methods) {
                    boolean bIsInstance = classNode.name.contains(MyCheck.C.dec("k1WjzFOAT70LrZugQupmucT8Hs4UFI/x9Xu0NJ2SyUs="));
                    if ((
                            bIsInstance
                                    && methodx.desc
                                        .equals(
                                            MyCheck.C.dec(
                                                "UceTux3RViKBlhoyFYAy6YK23kuVc1yBi6UmwR1G10l2wfOz3/ImKGOEiGye0BkYbGtkI5cOW5E4pw7vw+1i5vZIXC+iboQ1qykOR2U85nE="
                                            )
                                        )
                                || !bIsInstance
                                    && methodx.desc
                                        .equals(MyCheck.C.dec("UceTux3RViKBlhoyFYAy6V+5nuysPPPHTEyaLVUKKOn1WKEf7Ksv9stMEFFfZh9tZtr3rTHd8j+PB0fYX02zhw=="))
                        )
                        && methodx.name.startsWith(MyCheck.C.dec("lC9LiWfAi35+jLXEWKO3/A=="))) {
                        InsnList insnListx = new InsnList();
                        LabelNode L_returnAutox = new LabelNode();
                        LabelNode L_continuex = new LabelNode();
                        LabelNode L_PopAndContinuex = new LabelNode();
                        if (bIsInstance) {
                            insnListx.add(new VarInsnNode(25, 1));
                            insnListx.add(new JumpInsnNode(198, L_continuex));
                            insnListx.add(new VarInsnNode(25, 1));
                            insnListx.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("0xntWvA5bLXMsOg/X56/9A=="),
                                    MyCheck.C.dec("pZeveSeTCq6/krkWq2VvIxkLkuqDHl7wHeSbrTzejHs="),
                                    false
                                )
                            );
                        } else {
                            insnListx.add(new VarInsnNode(25, 0));
                            insnListx.add(
                                new FieldInsnNode(
                                    180,
                                    classNode.name,
                                    MyCheck.C.dec("fbzdA/Bd72esdWvAaEmZsQ=="),
                                    MyCheck.C.dec("bGtkI5cOW5E4pw7vw+1i5vZIXC+iboQ1qykOR2U85nE=")
                                )
                            );
                            insnListx.add(new TypeInsnNode(192, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                        }

                        insnListx.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );

                        for (String className : this.mClassNameStartsOfProtectedFieldsGet) {
                            insnListx.add(new InsnNode(89));
                            insnListx.add(new LdcInsnNode(className));
                            insnListx.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnListx.add(new JumpInsnNode(154, L_returnAutox));
                        }

                        insnListx.add(new InsnNode(87));
                        insnListx.add(new JumpInsnNode(167, L_continuex));
                        insnListx.add(L_returnAutox);
                        insnListx.add(new InsnNode(87));
                        this.insnListAddReturn(methodx, insnListx);
                        insnListx.add(L_PopAndContinuex);
                        insnListx.add(new InsnNode(87));
                        insnListx.add(L_continuex);
                        methodx.instructions.insert(insnListx);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var13) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_lookupCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("gIoR5H3VGbDgkaPVx5wz9g=="))) {
                for (MethodNode method : classNode.methods) {
                    if ((method.access & 256) == 0
                        && method.name.startsWith(MyCheck.C.dec("y1zMFBT4RA3PRpD03W9dVQ=="))
                        && method.desc.equals(MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlkUfGxMkc5F1qMFAldPgkm6yod3IuqoekLyyLjY9UR9q"))) {
                        InsnList insnList = new InsnList();
                        LabelNode L_continue = new LabelNode();
                        LabelNode L_popAndContinue = new LabelNode();
                        LabelNode L_tryStart = new LabelNode();
                        LabelNode L_tryEnd = new LabelNode();
                        LabelNode L_catch = new LabelNode();
                        insnList.add(L_tryStart);
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(new LdcInsnNode(Type.getType(MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj"))));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("o5S0cN3qLUfVBF1a3/G3YGba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMCkWiX+FWyoa7TeceRoeQ09PtyyR5S3pC7wkXXmoGQys"),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("gIoR5H3VGbDgkaPVx5wz9g=="),
                                MyCheck.C.dec("5/6QjI6wEgKHn2t/DnOMqw=="),
                                MyCheck.C.dec("9JZoHZoHCjLesNCO4Mq3VoczYHNfjgHzq8/VIMhlcz9FI384mvVTYiWP7cnwwSip"),
                                false
                            )
                        );
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new JumpInsnNode(166, L_continue));
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(new LdcInsnNode(Type.getType(MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj"))));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("o5S0cN3qLUfVBF1a3/G3YGba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMCkWiX+FWyoa7TeceRoeQ09PtyyR5S3pC7wkXXmoGQys"),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("gIoR5H3VGbDgkaPVx5wz9g=="),
                                MyCheck.C.dec("uGuPkfUJT4reNCFPEuz/QzQJ9fqMmvMX3TOlGLt/8Ck="),
                                MyCheck.C.dec("9JZoHZoHCjLesNCO4Mq3Viv99k+iuDxUFl9ocILE9r8="),
                                false
                            )
                        );
                        insnList.add(new VarInsnNode(22, 2));
                        insnList.add(new InsnNode(148));
                        insnList.add(new JumpInsnNode(154, L_continue));
                        List<String> CallerWhiteList = new ArrayList<>(
                            List.of(
                                MyCheck.C.dec("N+vOQTgfVcFEc4zqZch+nt9EookWi2yD3U1ejiC+XRg="),
                                MyCheck.C.dec("bPvxrgt/PQvX10z1vqHvD0XNG9uN+6Of/2h/aSTLOY1m2vetMd3yP48HR9hfTbOH"),
                                MyCheck.C.dec("vmAuC1uyVAyFctCFnDzcThFYwcuiq2erjryVqDH5wJdZkHyINRMUEEu9h7ZrNqG5")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("fdzbK6qUW+78VdHx2tK/FQ=="),
                                MyCheck.C.dec("Z0dHxxcJPly7JmcSCpnwe2O7mBIjcbDFHs89DfCagV0="),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("FJA7d/gCp0ZfOUgZr+4kcw=="),
                                MyCheck.C.dec("Xl8Hb2GG0zOWXTbAJ+ocy5yhXVuySZbfR7+ae2Qj/ARm2vetMd3yP48HR9hfTbOH"),
                                false
                            )
                        );
                        insnList.add(new InsnNode(5));
                        insnList.add(new InsnNode(50));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                                MyCheck.C.dec("zFL1G6W6qIQGKn7NKhLfaw=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );

                        for (String callerWhite : CallerWhiteList) {
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(callerWhite));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_popAndContinue));
                        }

                        insnList.add(new InsnNode(87));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/oK3ppljznvOylwEgqkD0mg="),
                                MyCheck.C.dec("BCOSGvIAIakC04TeqHHDkQ=="),
                                MyCheck.C.dec("FJwUxiWZlWSUKbjlyp8U0MIYFVM9CtHzGIcd9HbaNz0pOVpL348DBgvLsLbFZjbT"),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_tryEnd);
                        insnList.add(L_catch);
                        insnList.add(new InsnNode(87));
                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_popAndContinue);
                        insnList.add(new InsnNode(87));
                        insnList.add(L_continue);
                        method.instructions.insertBefore(method.instructions.getFirst(), insnList);
                        method.tryCatchBlocks
                            .add(new TryCatchBlockNode(L_tryStart, L_tryEnd, L_catch, MyCheck.C.dec("z8jIKbWPBhnUnB3W8UpXkAy4WFX6+xYfIu62BRBQohg=")));
                        bChanged = true;
                    }
                }
            } else if (classNode.name.startsWith(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"))) {
                for (MethodNode methodx : classNode.methods) {
                    if (methodx.name.equals(MyCheck.C.dec("zbkJVgfiF3VV2kuqSoePBg=="))
                            && methodx.desc
                                .equals(
                                    MyCheck.C.dec(
                                        "ZGfSxE0vJdk3ybd3zC9mVcRAsfVjdzWpVdfkN6cPyt09KakL3S7bUe9N+BiNFrl2nMUIa+NQy5a4mtJIUO/8nQa16w2A+qiRWGPqFN1i/nfCGBVTPQrR8xiHHfR22jc9U0bJu972ae61PbdUXZDGAg=="
                                    )
                                )
                        || methodx.name.equals(MyCheck.C.dec("EyrINOjMxdqPZuFaxmueBQ=="))
                            && methodx.desc
                                .equals(
                                    MyCheck.C.dec(
                                        "ZGfSxE0vJdk3ybd3zC9mVcRAsfVjdzWpVdfkN6cPyt09KakL3S7bUe9N+BiNFrl2nMUIa+NQy5a4mtJIUO/8nQa16w2A+qiRWGPqFN1i/nfCGBVTPQrR8xiHHfR22jc9U0bJu972ae61PbdUXZDGAg=="
                                    )
                                )
                        || methodx.name.equals(MyCheck.C.dec("2TjO56qxWOL9iPtOA+SLXA=="))
                            && methodx.desc
                                .equals(
                                    MyCheck.C.dec(
                                        "ZGfSxE0vJdk3ybd3zC9mVUELzqyF8BmUN1uKT34bugwTeu69ib3eksK6A2xQ+aFzaGaPbpyGn9wD5Yo4aLOI3oAFyZn+AV2Z3wacL3Iwb10="
                                    )
                                )) {
                        boolean bConstructor = methodx.name.equals(MyCheck.C.dec("2TjO56qxWOL9iPtOA+SLXA=="));
                        boolean bVirtual = methodx.name.contains(MyCheck.C.dec("ajV2kyXDxYAbyt72dPvs1w=="));
                        InsnList insnList = new InsnList();
                        LabelNode L_continue = new LabelNode();
                        LabelNode L_popAndContinue = new LabelNode();
                        LabelNode L_returnChangedMethod = new LabelNode();
                        LabelNode L_returnEmptyMethod = new LabelNode();
                        int varIndex1Class = 1;
                        int varIndex2String = 2;
                        int varIndex3MethodType = 3;
                        if (bConstructor) {
                            varIndex3MethodType = 2;
                            varIndex2String = -1;
                        }

                        insnList.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"),
                                MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ=="),
                                MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj")
                            )
                        );
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(new JumpInsnNode(165, L_continue));
                        List<String> watchListClass = new ArrayList<>(
                            List.of(MyCheck.C.dec("G7S4bU7nGDyHu0LBKGnTFQ=="), MyCheck.C.dec("B70E8CCpOT3ZhWklioGRNw=="))
                        );

                        for (String packageName : MyLib2.gModPackageNamesFromJar) {
                            if (!MyLib2.isThisNameMyMOD(packageName)) {
                                watchListClass.add(packageName);
                            }
                        }

                        for (String className : watchListClass) {
                            insnList.add(new VarInsnNode(25, varIndex1Class));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_returnChangedMethod));
                        }

                        insnList.add(new JumpInsnNode(167, L_continue));
                        insnList.add(L_returnChangedMethod);
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("fdzbK6qUW+78VdHx2tK/FQ=="),
                                MyCheck.C.dec("Z0dHxxcJPly7JmcSCpnwe2O7mBIjcbDFHs89DfCagV0="),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("FJA7d/gCp0ZfOUgZr+4kcw=="),
                                MyCheck.C.dec("Xl8Hb2GG0zOWXTbAJ+ocy5yhXVuySZbfR7+ae2Qj/ARm2vetMd3yP48HR9hfTbOH"),
                                false
                            )
                        );
                        insnList.add(new InsnNode(5));
                        insnList.add(new InsnNode(50));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                                MyCheck.C.dec("zFL1G6W6qIQGKn7NKhLfaw=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );

                        for (String callerClass : List.of(
                            MyCheck.C.dec("G7S4bU7nGDyHu0LBKGnTFQ=="),
                            MyCheck.C.dec("B70E8CCpOT3ZhWklioGRNw=="),
                            MyCheck.C.dec("NFBrqe/g8DYVsbIdRY7x+g=="),
                            MyCheck.C.dec("tQKQ+4PPUxNwrB4E4tcc5w=="),
                            MyCheck.C.dec("pGd7vi7MFxWEWJd2lxnpe260eqAIKw5QqXoixZOxcNk="),
                            MyCheck.C.dec("IVNGqpeJOcY1aU+wQrHNhxXixlBahYzjAC5OZBKZE5M=")
                        )) {
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(callerClass));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                    MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                    MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                    false
                                )
                            );
                            insnList.add(new JumpInsnNode(154, L_popAndContinue));
                        }

                        insnList.add(new InsnNode(89));
                        insnList.add(new VarInsnNode(25, varIndex1Class));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_popAndContinue));
                        insnList.add(new InsnNode(87));
                        if (bConstructor) {
                            insnList.add(
                                new FieldInsnNode(
                                    178,
                                    MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"),
                                    MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ=="),
                                    MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj")
                                )
                            );
                            insnList.add(new VarInsnNode(25, varIndex1Class));
                            insnList.add(new VarInsnNode(25, varIndex3MethodType));
                            insnList.add(new MethodInsnNode(182, classNode.name, methodx.name, methodx.desc, false));
                            insnList.add(new InsnNode(176));
                        } else {
                            LabelNode L_returnUsingStrongLookup = new LabelNode();

                            for (String methodName : List.of(MyCheck.C.dec("uk+5GJy4FrQ7B3GaiXEYcg=="), MyCheck.C.dec("bKaw6pcWyX3XbBSP6cRWWg=="))) {
                                insnList.add(new LdcInsnNode(methodName));
                                insnList.add(new VarInsnNode(25, varIndex2String));
                                insnList.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                        MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                        false
                                    )
                                );
                                insnList.add(new JumpInsnNode(154, L_returnUsingStrongLookup));
                            }

                            insnList.add(new JumpInsnNode(167, L_returnEmptyMethod));
                            insnList.add(L_returnUsingStrongLookup);
                            insnList.add(
                                new FieldInsnNode(
                                    178,
                                    MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"),
                                    MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ=="),
                                    MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj")
                                )
                            );
                            insnList.add(new VarInsnNode(25, varIndex1Class));
                            insnList.add(new VarInsnNode(25, varIndex2String));
                            insnList.add(new VarInsnNode(25, varIndex3MethodType));
                            insnList.add(new MethodInsnNode(182, classNode.name, methodx.name, methodx.desc, false));
                            insnList.add(new InsnNode(176));
                        }

                        insnList.add(L_returnEmptyMethod);
                        insnList.add(new VarInsnNode(25, varIndex3MethodType));
                        if (bVirtual) {
                            insnList.add(new InsnNode(3));
                            insnList.add(new InsnNode(4));
                            insnList.add(new TypeInsnNode(189, MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ==")));
                            insnList.add(new InsnNode(89));
                            insnList.add(new InsnNode(3));
                            insnList.add(new VarInsnNode(25, varIndex1Class));
                            LabelNode L_NotJdkUnsafe = new LabelNode();
                            insnList.add(new InsnNode(89));
                            insnList.add(new LdcInsnNode(Type.getType(MyCheck.C.dec("eHjFECzzKClirnSxlKkoZVLoVFyAn1O7DFW4dq/j+2A="))));
                            insnList.add(new JumpInsnNode(166, L_NotJdkUnsafe));
                            insnList.add(new InsnNode(87));
                            insnList.add(new LdcInsnNode(Type.getType(MyCheck.C.dec("bGtkI5cOW5E4pw7vw+1i5vZIXC+iboQ1qykOR2U85nE="))));
                            insnList.add(L_NotJdkUnsafe);
                            insnList.add(new InsnNode(83));
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/uXt+nYElWX0HP+WOIN1Thc="),
                                    MyCheck.C.dec("cHCC478onN4CE5pBqWWcaV6SIPMocB91pBZ8mqzaOp4="),
                                    MyCheck.C.dec("korUqQY5q6fhMTkidx59rY0+pwyPHrje5KvGz1xBxDca+i8t5vlbOyDDP9ZLe3QtRs79tyY81/SCOyDuaGX4TA=="),
                                    false
                                )
                            );
                        }

                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/oK3ppljznvOylwEgqkD0mg="),
                                MyCheck.C.dec("JiY1tdQiTj/n4yulz8bYOw=="),
                                MyCheck.C.dec("UceTux3RViKBlhoyFYAy6dSUq6KpR21T7D5RC8mA9AKl8i4wkY154pnZg6HTCtj+FPROfH+z34+5bRTOB7RKdQ=="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_popAndContinue);
                        insnList.add(new InsnNode(87));
                        insnList.add(L_continue);
                        methodx.instructions.insert(insnList);
                        bChanged = true;
                    } else if ((
                            methodx.name.equals(MyCheck.C.dec("PG6Q05kTBcXwzd+I4l8TYrhOtqZD1lL9bsZz1usLF1M="))
                                || methodx.name.equals(MyCheck.C.dec("29+0ajPniaI/+We09ClwqA=="))
                        )
                        && methodx.desc
                            .equals(
                                MyCheck.C.dec(
                                    "ZGfSxE0vJdk3ybd3zC9mVcRAsfVjdzWpVdfkN6cPyt1UyWonVywZTih3s4Q91HnFQLk7+N6XLxC6U/0ujNTG7lfVrl6FcC3ozx8/xnY0UkdGzv23JjzX9II7IO5oZfhM"
                                )
                            )) {
                        InsnList insnListx = new InsnList();
                        LabelNode L_continuex = new LabelNode();
                        LabelNode L_returnNormalUnsafe = new LabelNode();
                        LabelNode L_returnUsingImplLookup = new LabelNode();
                        insnListx.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"),
                                MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ=="),
                                MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj")
                            )
                        );
                        insnListx.add(new VarInsnNode(25, 0));
                        insnListx.add(new JumpInsnNode(165, L_continuex));
                        insnListx.add(new VarInsnNode(25, 1));
                        insnListx.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnListx.add(new LdcInsnNode(MyCheck.C.dec("F+xPdnrYfZecloL5PQE7Vg==")));
                        insnListx.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                false
                            )
                        );
                        insnListx.add(new LdcInsnNode(MyCheck.C.dec("+s63eLAA4bYdArBBBlwTVmxt0+pBXMELLAW7prSRCZU=")));
                        insnListx.add(new VarInsnNode(25, 2));
                        insnListx.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                false
                            )
                        );
                        insnListx.add(new InsnNode(126));
                        insnListx.add(new JumpInsnNode(154, L_returnNormalUnsafe));
                        Map<String, List<String>> whiteList = new LinkedHashMap<>();
                        whiteList.put(
                            MyCheck.C.dec("utnPyxQXwwvVQB6l0f4S8ZKdgfYssnfVRVaZzZuS8cnkbYP6rbp4X5Y77l/IfdJH"),
                            List.of(MyCheck.C.dec("2esdmdIR9+SQPfFEqTjSFg=="))
                        );
                        whiteList.put(MyCheck.C.dec("GWJcVbVHleE6ibnvwVVKNZB6cF41rO/AhQbR2+Q4oz8="), List.of(MyCheck.C.dec("XynLK947d01myKqpeEzU0Q==")));
                        whiteList.put(
                            MyCheck.C.dec("HX/NscF0PKhXw+WfY1rYKWba960x3fI/jwdH2F9Ns4c="),
                            List.of(MyCheck.C.dec("dgZ2sfxKOzdLZncNLoPfQQ=="), MyCheck.C.dec("nhFz6Wlf20Zj2a4Qx/JRWg=="))
                        );
                        whiteList.put(
                            MyCheck.C.dec("ihCavjHzE91nddXSqoNj56/BNUkM27Dt8uo7wKjyqUE="),
                            List.of(
                                MyCheck.C.dec("yq6AqONDLrSg2q8OaxhEwQ=="), MyCheck.C.dec("xLZhvTb83brCbcL2aGAa6Q=="), MyCheck.C.dec("DFlzMxO8CHbs7uCVe84sIw==")
                            )
                        );
                        whiteList.put(
                            MyCheck.C.dec("OpdLmIHbeZvwi0P8bsUEF2OP2oSyzot1sWMMxrhbPTBkMWwFDQOK9DMot9KRi7rT"),
                            List.of(
                                MyCheck.C.dec("LKHuj/nQ0EUq8lS2cwYI4mPcvq9u9EJcBg1B8eYpgkU="),
                                MyCheck.C.dec("sGr2BtqI1XRfCOJcDxYQ6U8Zsge+MQ2LUwtJc6bPppM="),
                                MyCheck.C.dec("0haK2DWhwb6bz1vH0DerHw=="),
                                MyCheck.C.dec("yq6AqONDLrSg2q8OaxhEwQ=="),
                                MyCheck.C.dec("1OCHuxo77W5NmWlEfRNfc2BHeFM/clJ2b+Fy4toHt8s="),
                                MyCheck.C.dec("Q1tt49S7ozjsGx2o1Ua/IQ==")
                            )
                        );
                        whiteList.put(
                            MyCheck.C.dec("OpdLmIHbeZvwi0P8bsUEF2OP2oSyzot1sWMMxrhbPTBaIA5qT6sT8U+rndO+xwAPTgbPdSETHSO/DUNgtV04CQ=="),
                            List.of(MyCheck.C.dec("wh1L5Y+a6jX1E1WHKDOCRQ=="), MyCheck.C.dec("uHAQbTqaqeTyuI8cc0GfvQ=="))
                        );
                        whiteList.put(
                            MyCheck.C.dec("OpdLmIHbeZvwi0P8bsUEF8u90VXiX2S8vsx2eR1l4lVm2vetMd3yP48HR9hfTbOH"),
                            List.of(MyCheck.C.dec("jzgDfJ8Mss+b63bcj3kN/Q=="))
                        );
                        whiteList.put(MyCheck.C.dec("aoR5+NJbdpGPbRcsIVlBVYXz8LEU1tssgx77HK+ZH3M="), List.of(MyCheck.C.dec("j6JlcP2IZxw7icVM9nTVKA==")));
                        whiteList.put(
                            MyCheck.C.dec("SSg69TV0XqispkkZUExoLg=="),
                            List.of(MyCheck.C.dec("NJSxV5fu9xspv9M/uI25eA=="), MyCheck.C.dec("nfy2mc/6BQAX9Hym0zn3gg=="))
                        );
                        whiteList.put(MyCheck.C.dec("AUPST1cNurXqLXQxbqLY6gYbgM+85lbkqL0mFwP8/SA="), List.of(MyCheck.C.dec("mREPrUvPxnlpjLYO16gg4Q==")));
                        whiteList.put(
                            MyCheck.C.dec("GP9N0WYOzH1CHzTE3PNCBrUKCchGwsrWt6SpxvYZLvnkbYP6rbp4X5Y77l/IfdJH"),
                            List.of(MyCheck.C.dec("52TkjA/IXSrEmxr6jAn9+A=="))
                        );
                        whiteList.put(MyCheck.C.dec("OpdLmIHbeZvwi0P8bsUEF0pgvn9N4lZGAlHqoiLdFZ0="), List.of(MyCheck.C.dec("fXJUwRaQ8dOenH22XdtRNg==")));
                        whiteList.put(
                            MyCheck.C.dec("aoR5+NJbdpGPbRcsIVlBVS2HECXXKn4NcviA+oWIJEs="),
                            List.of(MyCheck.C.dec("x4KRPPsxwQ+vq/B9TVHGIA=="), MyCheck.C.dec("oCas54o+i8CG1Hsh+RqCUA=="))
                        );
                        whiteList.put(
                            MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScExlBwccePWEel3ztcYhqxc="),
                            List.of(MyCheck.C.dec("SGENqXmh8rD4kiddbjtsGA=="), MyCheck.C.dec("ESlWUtWPzRJbypFcmcYZMA=="))
                        );
                        whiteList.put(
                            MyCheck.C.dec("ZkKsQ4hKeik0JcZH/Q/fmAIA4KeyVmakNv5S6fLuUSSC7q4RJ+ctIk1plQFyYlx9"),
                            List.of(
                                MyCheck.C.dec("SGENqXmh8rD4kiddbjtsGA=="),
                                MyCheck.C.dec("q7iMxC9PC12lBaYhrgt/QQ=="),
                                MyCheck.C.dec("ajdfbU0Tc/4LTwSvB5h27A=="),
                                MyCheck.C.dec("lGUzIRmkgMhoUatcl/k5Gw==")
                            )
                        );
                        whiteList.put(
                            MyCheck.C.dec("F2MzbZEsQj1M9NgFy8MScGatuntb2Q7h/O7l+3/MJka8rI7e0XnXw1N8vIajoaWz"),
                            List.of(MyCheck.C.dec("TT46b9dBDfxC4gTy3hoG4w=="), MyCheck.C.dec("4R0CkLeiv8v06ZEApd5qDw=="))
                        );
                        whiteList.put(
                            MyCheck.C.dec("NYLD1kZJHO2W98vy4B4Pu2NTW54a7s2XQvRLGETSpRiXdXT1GpfDMwtvjp57ok0h"),
                            List.of(MyCheck.C.dec("T4GafoRjXZSnaUteHjXEpg=="), MyCheck.C.dec("3F7onpE4IL/UKC1TzQh/SQ=="))
                        );
                        whiteList.put(
                            MyCheck.C.dec("0BiTQekQu93d0c1aPxqZJw3VJra+ZA9LGxaqn5GcE2QhvKiCzIft/dIfpWY9QwPV"),
                            List.of(
                                MyCheck.C.dec("CAnyEfvkfw5vW5kbUQ5fEWba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("R5g/FBCHrqN+ifpUtF2xJQ=="),
                                MyCheck.C.dec("bmrlpRYeUVF0++bJ3CeTpQ=="),
                                MyCheck.C.dec("oRbxxNO19ToAc9Jv2uyYBA=="),
                                MyCheck.C.dec("yCRq4Mmt8ZfUn5k7l17PGuw1FSBTQ03qmW5A0OyJ06E="),
                                MyCheck.C.dec("Pw03VucP7VZfZa6kVdk0jA==")
                            )
                        );

                        for (Entry<String, List<String>> entry : whiteList.entrySet()) {
                            String className = entry.getKey();

                            for (String fieldName : entry.getValue()) {
                                insnListx.add(new VarInsnNode(25, 1));
                                insnListx.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                        MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                        MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                        false
                                    )
                                );
                                insnListx.add(new LdcInsnNode(className));
                                insnListx.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                        MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                        false
                                    )
                                );
                                insnListx.add(new LdcInsnNode(fieldName));
                                insnListx.add(new VarInsnNode(25, 2));
                                insnListx.add(
                                    new MethodInsnNode(
                                        182,
                                        MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                                        MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                                        false
                                    )
                                );
                                insnListx.add(new InsnNode(126));
                                insnListx.add(new JumpInsnNode(154, L_returnUsingImplLookup));
                            }
                        }

                        insnListx.add(new JumpInsnNode(167, L_continuex));
                        insnListx.add(L_returnNormalUnsafe);
                        insnListx.add(new LdcInsnNode(MyCheck.C.dec("vjE77EKZnxE5hsLrij0rcg==")));
                        insnListx.add(new VarInsnNode(58, 2));
                        insnListx.add(new VarInsnNode(25, 1));
                        insnListx.add(new VarInsnNode(58, 3));
                        insnListx.add(new JumpInsnNode(167, L_returnUsingImplLookup));
                        insnListx.add(L_returnUsingImplLookup);
                        insnListx.add(
                            new FieldInsnNode(
                                178,
                                MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"),
                                MyCheck.C.dec("2CLtroq+UJRLqyKbq8U9CQ=="),
                                MyCheck.C.dec("aGaPbpyGn9wD5Yo4aLOI3kBYLgbZlUxGp5+FDK8fm1A/CZWRiH2GNtCPeQegPcLj")
                            )
                        );
                        insnListx.add(new VarInsnNode(25, 1));
                        insnListx.add(new VarInsnNode(25, 2));
                        insnListx.add(new VarInsnNode(25, 3));
                        insnListx.add(new MethodInsnNode(182, classNode.name, methodx.name, methodx.desc, false));
                        insnListx.add(new InsnNode(176));
                        insnListx.add(L_continuex);
                        methodx.instructions.insert(insnListx);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var19) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_methodHandleCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("pfIuMJGNeeKZ2YOh0wrY/tyMRA+nuINn6yAraGWnoil60hVxZUOIqf+tuUvjApBG"))) {
                for (MethodNode method : classNode.methods) {
                    if ((
                            method.name.equals(MyCheck.C.dec("UwQFYJqShTM1vh4BRwPS4FZIiJiYBh8CJoRV2h9CMWg="))
                                || method.name.equals(MyCheck.C.dec("vHWHPG4e0qcWbNQLPt/hGaK0ZtiDb+zWyuOaRY2w5UE="))
                        )
                        && method.desc.endsWith(MyCheck.C.dec("i4bIhZH88mcbPjqfMpx1xTVkUwisowjlilkltEQPNXFm2vetMd3yP48HR9hfTbOH"))) {
                        for (AbstractInsnNode insn : method.instructions.toArray()) {
                            if (insn.getOpcode() == 176) {
                                InsnList insnList = new InsnList();
                                insnList.add(
                                    new LdcInsnNode(MyCheck.C.dec("nYi1UNL/qXUrx7o32v+AQw==") + method.name + MyCheck.C.dec("zstbThnwsgyZe4LCc9S3QA=="))
                                );
                                insnList.add(
                                    new MethodInsnNode(
                                        184,
                                        MyCheck.C.dec("ai7EaHeYu72UwqWo3zwk6Q=="),
                                        MyCheck.C.dec("bUJBE8A5pkfmY7GLCw+lr2ba960x3fI/jwdH2F9Ns4c="),
                                        MyCheck.C.dec(
                                            "UceTux3RViKBlhoyFYAy6TVkUwisowjlilkltEQPNXFEOO67wFU+OMVp6+QAM2S0k9O2t6r76qmffHpUkNdYhfOH4mAq7vBKtKo+MO/h1DhGzv23JjzX9II7IO5oZfhM"
                                        ),
                                        false
                                    )
                                );
                                method.instructions.insertBefore(insn, insnList);
                                bChanged = true;
                            }
                        }
                    }
                }
            }
        } catch (Throwable var10) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_hiddenCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("ljMyRMt6rWKiAwEz3xo1LWba960x3fI/jwdH2F9Ns4c="))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("In8Sx1SPTk4O5nUwighlCg=="))
                        && method.desc
                            .equals(
                                MyCheck.C.dec(
                                    "ZGfSxE0vJdk3ybd3zC9mVVJ5VzWEIWVkTEXWKKlwOCu18HpeM3SqZqYgWc7kPjeW0kO3HBVVBFsHCZHL6D65LJuD8yCBFdT+99i8vYQ9cTClkySYtearh6teZyuUT5YeyaZ/ufZKE2a0YhGjclYUIgz9jcf0bioEcUEFdEsPotQZC5Lqgx5e8B3km6083ox7"
                                )
                            )) {
                        LabelNode L_continue = new LabelNode();
                        InsnList insnList = new InsnList();
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("99bOi5nKlDB7RYf10tz/Dw==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_continue));
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("BvQt1svriIXf6eK6gdpkmA==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_continue));
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("RLZQXKeM0qj05TDF0oPpdBwwQQcskbrk+MSvszS6ZIM=")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("2zcN2qPT70YeSrhqNkhQqQ=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_continue));
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("MH73vtRI/BssypsyS0XDng==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_continue));
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("d8m67omzvTrM5GTNc7MyApAeKy91mnSGHVrQI4cfbZT4EvwVu4+08/MG2hLhqbmx")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                                false
                            )
                        );
                        insnList.add(new JumpInsnNode(154, L_continue));
                        insnList.add(new VarInsnNode(25, 1));
                        insnList.add(new VarInsnNode(25, 2));
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(new VarInsnNode(25, 4));
                        insnList.add(new InsnNode(3));
                        insnList.add(new VarInsnNode(25, 4));
                        insnList.add(new InsnNode(190));
                        insnList.add(new VarInsnNode(25, 5));
                        insnList.add(new VarInsnNode(21, 6));
                        insnList.add(new VarInsnNode(21, 7));
                        insnList.add(new VarInsnNode(25, 8));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("ai7EaHeYu72UwqWo3zwk6Q=="),
                                MyCheck.C.dec("OUbYO39AjXkUWRReS9GJZw=="),
                                MyCheck.C.dec(
                                    "ZGfSxE0vJdk3ybd3zC9mVVJ5VzWEIWVkTEXWKKlwOCu18HpeM3SqZqYgWc7kPjeW/FBfzKFaq8rfYKPNmC02uJb7ZAJaAPbbDEVd8imkYwUV5zFFlQ51w+EQjQRW7E0w102KHxfUwg1dEf2qJ/SsuTR0DoWONDJhKhvc1FvX1SinmZNic65uBupmJNMR+7TI"
                                ),
                                false
                            )
                        );
                        insnList.add(new InsnNode(176));
                        insnList.add(L_continue);
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var7) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_badCall(ClassNode classNode) {
        if (!MyLib2.isThisNameOtherBadMOD(classNode.name.replace('/', '.'))) {
            return false;
        } else {
            boolean bChanged = false;

            try {
                for (MethodNode method : classNode.methods) {
                    for (AbstractInsnNode insn : method.instructions.toArray()) {
                        if (insn instanceof MethodInsnNode) {
                            MethodInsnNode mInsn = (MethodInsnNode)insn;
                            boolean needChange = false;
                            String calleeName = mInsn.owner.replace(MyCheck.C.dec("Wo+Eco5sTCT/PJejuDGBNQ=="), MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA=="))
                                + MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")
                                + mInsn.name
                                + MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==");

                            for (String badCalleeName : this.mBadCalleeNames) {
                                if (calleeName.startsWith(badCalleeName)) {
                                    needChange = true;
                                    break;
                                }
                            }

                            if (!needChange && mInsn.owner.equals(classNode.name) && classNode.superName != null) {
                                String calleeName2 = classNode.superName
                                        .replace(MyCheck.C.dec("Wo+Eco5sTCT/PJejuDGBNQ=="), MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA=="))
                                    + MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")
                                    + mInsn.name
                                    + MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==");

                                for (String badCalleeNamex : this.mBadCalleeNames) {
                                    if (calleeName2.startsWith(badCalleeNamex)) {
                                        needChange = true;
                                        break;
                                    }
                                }
                            }

                            if (needChange) {
                                this.makeMethodCallEmpty(method, mInsn);
                                bChanged = true;
                            }
                        }
                    }
                }
            } catch (Throwable var15) {
                if (bChanged) {
                    bChanged = false;
                }
            }

            return bChanged;
        }
    }

    private boolean tranNodes_dllLoadCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.contains(MyCheck.C.dec("TYYcGywFvixjR9vCE2u/iASh+h15bZFf59d8hc/fBAdaIA5qT6sT8U+rndO+xwAPTgbPdSETHSO/DUNgtV04CQ=="))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("EDSSYhe3BmaRLyUu5EEfHg=="))) {
                        LabelNode L_continue = new LabelNode();
                        InsnList insnList = new InsnList();

                        for (String className : List.of(
                            MyCheck.C.dec("GOBda+PiyEeGbskFBmMY4A=="),
                            MyCheck.C.dec("V83NBDPZiM5kNGfbS2jbbw=="),
                            MyCheck.C.dec("nysD8BLHFCl++30VvUEhJA=="),
                            MyCheck.C.dec("DBThY0JMc6/nQ+jp1Kow8g=="),
                            MyCheck.C.dec("NFBrqe/g8DYVsbIdRY7x+g==")
                        )) {
                            insnList.add(new VarInsnNode(25, 0));
                            insnList.add(
                                new FieldInsnNode(
                                    180,
                                    classNode.name,
                                    MyCheck.C.dec("uHAQbTqaqeTyuI8cc0GfvQ=="),
                                    MyCheck.C.dec("wYlg45U5fem1r/6/llbJZFNGybve9mnutT23VF2QxgI=")
                                )
                            );
                            insnList.add(
                                new MethodInsnNode(
                                    182,
                                    MyCheck.C.dec("Jki9/OUkndx/bw0S74EJqQ=="),
                                    MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                    MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                    false
                                )
                            );
                            insnList.add(new LdcInsnNode(className));
                            this.insnListAdd_String_startsWith_thenJump(insnList, L_continue);
                        }

                        this.insnListAddReturn(method, insnList);
                        insnList.add(L_continue);
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var10) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_dllInvokeCallee(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.startsWith(MyCheck.C.dec("aECHnA84Eh1Y3hfxATEj+6AS5kGi0RxJSrIFMA6IMYQ="))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("oobS1MQOVhDY3bJVjXV/0w=="))) {
                        LabelNode L_popAndContinue = new LabelNode();
                        InsnList insnList = new InsnList();
                        insnList.add(new VarInsnNode(25, 0));
                        insnList.add(
                            new FieldInsnNode(
                                180,
                                MyCheck.C.dec("aECHnA84Eh1Y3hfxATEj+6AS5kGi0RxJSrIFMA6IMYQ="),
                                MyCheck.C.dec("kUIrPC+SulDkifVkhOjhKQ=="),
                                MyCheck.C.dec("pTBPeOzWLF8sNi4sMmgDM4r8by8/iqGzXSOR+K+f+BU=")
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("ZKRswVsxhf1QM3jbhWiMOiPuBH4fhBYqxlLqvHgVtfo="),
                                MyCheck.C.dec("W4rNpPrFrNfslgF6TeHkeA=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                false
                            )
                        );
                        insnList.add(new VarInsnNode(25, 2));
                        insnList.add(new VarInsnNode(25, 3));
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("ai7EaHeYu72UwqWo3zwk6Q=="),
                                MyCheck.C.dec("AZbWgDwMSkm0uAio0werZYfTGCWnKXiRdJ7MxTHEdqU="),
                                MyCheck.C.dec(
                                    "RQ+TKxj9T9huqeAYlKNwMCpY6mZ3+dySO3f4gDkBt3mBBjjzffYl4is4P3Fw4iAZ9VihH+yrL/bLTBBRX2YfbX69rZ276dPTPw/6/eYR1Nh1XGAarop4nsO6FHig9SIf"
                                ),
                                false
                            )
                        );
                        insnList.add(new InsnNode(89));
                        insnList.add(new JumpInsnNode(198, L_popAndContinue));
                        insnList.add(new InsnNode(3));
                        insnList.add(new InsnNode(50));
                        insnList.add(new InsnNode(176));
                        insnList.add(L_popAndContinue);
                        insnList.add(new InsnNode(87));
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var7) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    public static Object[] hook_LibraryHandler_invoke(String nativeLibraryName, Method method, Object[] inArgs) {
        if (MyLib2.isCalledFromOtherModWithin10()) {
            Object[] returnValue = new Object[1];
            Class<?> returnType = method.getReturnType();
            if (returnType == void.class) {
                returnValue[0] = null;
            } else if (returnType == boolean.class) {
                returnValue[0] = false;
            } else if (returnType == byte.class) {
                returnValue[0] = (byte)0;
            } else if (returnType == short.class) {
                returnValue[0] = (short)0;
            } else if (returnType == char.class) {
                returnValue[0] = '\u0000';
            } else if (returnType == int.class) {
                returnValue[0] = 0;
            } else if (returnType == long.class) {
                returnValue[0] = 0L;
            } else if (returnType == float.class) {
                returnValue[0] = 0.0F;
            } else if (returnType == double.class) {
                returnValue[0] = 0.0;
            } else {
                returnValue[0] = null;
            }

            return returnValue;
        } else {
            return null;
        }
    }

    private boolean tranNodes_fixName1(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.equals(MyCheck.C.dec("6XNp4Js1mBDGRpcC5TU+6dD2x5iurz4doMSxhIZRMcf18EHWvOsfVCRicgV7wbvDlIX2rOfaE1YVSr8BDtJd3w=="))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("q5cBSsRx3mArkySPuqixQg=="))) {
                        InsnList insnList = new InsnList();
                        insnList.add(new VarInsnNode(25, 2));
                        insnList.add(
                            new MethodInsnNode(
                                185,
                                MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEo8iixkRCfefVzSAQVZWKhDxWgsV+IUI3Wi14mo4rSacL"),
                                MyCheck.C.dec("5S10eO+Unp2UJMKcGHDRQg=="),
                                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                                true
                            )
                        );
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("SS8TNNXb68f8K/7w1AlAMw==")));
                        insnList.add(new LdcInsnNode(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==")));
                        insnList.add(
                            new MethodInsnNode(
                                182,
                                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                                MyCheck.C.dec("Vih6oJ7YB3RKN3a146aR8w=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMDP1QKoaTPbOCCE7xabeIzMr0wPz5zz2uSeIcOueCT7tJisQ+LVKwWGONhYT1soxoQ=="),
                                false
                            )
                        );
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                MyCheck.C.dec("GdF/5C7+P57y9G/4ODWEo8iixkRCfefVzSAQVZWKhDxWgsV+IUI3Wi14mo4rSacL"),
                                MyCheck.C.dec("8iXNda7CQ8UBW/N4BUku1g=="),
                                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMG70pdiYEOKdsLqK6kPHqA9OVUc0MeEzUB9kALLq3GPk0TpoXMBBN4cy3ZQCErKPdw=="),
                                true
                            )
                        );
                        insnList.add(new VarInsnNode(58, 2));
                        method.instructions.insert(insnList);
                        bChanged = true;
                    }
                }
            }
        } catch (Throwable var6) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    private boolean tranNodes_tempXXXX(ClassNode classNode) {
        boolean bChanged = false;

        try {
            if (classNode.name.contains(MyCheck.C.dec("KqUJtX0IMWElyUGu3yj4AQ=="))) {
                for (MethodNode method : classNode.methods) {
                    if (method.name.equals(MyCheck.C.dec("4y1G8WwJoqL6Xi3qcu58gw=="))) {
                    }
                }
            }
        } catch (Throwable var5) {
            if (bChanged) {
                bChanged = false;
            }
        }

        return bChanged;
    }

    public static Instrumentation myCreateInstrumentationImpl() {
        return MyKeisou.gInstance;
    }

    public static void doNotResetThisClass(String className) {
        if (MyLib2.getCallerClass1() == MyPlugin.class) {
            gDoNotResetThisClass.add(className);
        }
    }

    public boolean canMakeMethodEmpty(MethodNode method) {
        return (method.access & 1280) == 0
            && !method.name.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="))
            && !method.name.equals(MyCheck.C.dec("d+0KueTGDEG+vXcATNKFnQ=="));
    }

    public void makeMethodEmpty(ClassNode classNode, MethodNode method) {
        boolean shouldInsertSuperCall = this.shouldInsertSuperCall(classNode, method);
        method.instructions.clear();
        method.tryCatchBlocks.clear();
        method.localVariables = null;
        InsnList insnList = new InsnList();
        Type returnType = Type.getReturnType(method.desc);
        if (shouldInsertSuperCall) {
            this.insnListAddSuperCall(classNode, method, insnList);
        } else {
            this.insnListAdd0null(method.desc, insnList);
        }

        insnList.add(new InsnNode(returnType.getOpcode(172)));
        method.instructions.insert(insnList);
    }

    private boolean shouldInsertSuperCall(ClassNode classNode, MethodNode method) {
        if (!MyLib2.isThisNameMinecraftVanilla(classNode.superName.replace('/', '.'))) {
            return false;
        } else {
            for (AbstractInsnNode insn : method.instructions) {
                if (insn instanceof MethodInsnNode mInsn
                    && mInsn.getOpcode() == 183
                    && mInsn.owner.equals(classNode.superName)
                    && mInsn.name.equals(method.name)
                    && mInsn.desc.equals(method.desc)) {
                    return true;
                }
            }

            return false;
        }
    }

    private void insnListAddSuperCall(ClassNode classNode, MethodNode method, InsnList insnList) {
        insnList.add(new VarInsnNode(25, 0));
        int index = 1;

        for (Type arg : Type.getArgumentTypes(method.desc)) {
            insnList.add(new VarInsnNode(arg.getOpcode(21), index));
            index += arg.getSize();
        }

        insnList.add(new MethodInsnNode(183, classNode.superName, method.name, method.desc, false));
    }

    private void insnListAdd0null(String desc, InsnList insnList) {
        Type returnType = Type.getReturnType(desc);
        switch (returnType.getSort()) {
            case 0:
            default:
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                insnList.add(new InsnNode(3));
                break;
            case 6:
                insnList.add(new InsnNode(11));
                break;
            case 7:
                insnList.add(new InsnNode(9));
                break;
            case 8:
                insnList.add(new InsnNode(14));
                break;
            case 9:
                Type elem = returnType.getElementType();
                insnList.add(new InsnNode(3));
                if (elem.getSort() != 10 && elem.getSort() != 9) {
                    int atype = switch (elem.getSort()) {
                        case 1 -> 4;
                        case 2 -> 5;
                        case 3 -> 8;
                        case 4 -> 9;
                        case 5 -> 10;
                        case 6 -> 6;
                        case 7 -> 11;
                        case 8 -> 7;
                        default -> 10;
                    };
                    insnList.add(new IntInsnNode(188, atype));
                } else {
                    insnList.add(new TypeInsnNode(189, elem.getInternalName()));
                }
                break;
            case 10:
                String internalName = returnType.getInternalName();
                if (internalName.equals(MyCheck.C.dec("RwABzRTHvPlcv+ORN6dQzg=="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("GeQr9jfEf5L60EXr2i2TTgppHGTXzgwGIhLdmbCBm0I=")));
                    insnList.add(new InsnNode(89));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("GeQr9jfEf5L60EXr2i2TTgppHGTXzgwGIhLdmbCBm0I="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("mVKyP346L68wepRjcs+5kg=="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("641duuv+U/jni9C64XTKfzQJ9fqMmvMX3TOlGLt/8Ck=")));
                    insnList.add(new InsnNode(89));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("641duuv+U/jni9C64XTKfzQJ9fqMmvMX3TOlGLt/8Ck="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("nknnI32rpsDhvHn3fDoPMQ=="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("qwbhasYwg7ck80jcYC2rYg3nw6zkQseT79lLu/Y8rF0=")));
                    insnList.add(new InsnNode(89));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("qwbhasYwg7ck80jcYC2rYg3nw6zkQseT79lLu/Y8rF0="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("b3ELkPYCsvP73GEp8zSkeQ=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="))) {
                    insnList.add(new LdcInsnNode(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==")));
                } else if (internalName.equals(MyCheck.C.dec("Ee2KJYP9x73pEjtF9H/9hcWrQpUaxpWznW62Q3oAvEc="))) {
                    insnList.add(
                        new MethodInsnNode(
                            184,
                            MyCheck.C.dec("Ee2KJYP9x73pEjtF9H/9hcWrQpUaxpWznW62Q3oAvEc="),
                            MyCheck.C.dec("JiY1tdQiTj/n4yulz8bYOw=="),
                            MyCheck.C.dec("oXevvpYKdakYVZ5XskbIA41H9mFvPBUSeLZ5NiR78Ok="),
                            false
                        )
                    );
                } else if (this.isEnumClassName(internalName)) {
                    if (internalName.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEIqkwj9Kg+Cpyv8DyMthx9arxFjCLk5PCADplsndts+I"))) {
                        insnList.add(new InsnNode(1));
                    } else {
                        insnList.add(
                            new MethodInsnNode(
                                184,
                                internalName,
                                MyCheck.C.dec("5I4RX8TRKM+4F2weJ8S7GQ=="),
                                MyCheck.C.dec("xT29YI+Wa0yl+EyzYWfhqA==") + internalName + MyCheck.C.dec("U0bJu972ae61PbdUXZDGAg=="),
                                false
                            )
                        );
                        insnList.add(new InsnNode(3));
                        insnList.add(new InsnNode(50));
                    }
                } else if (internalName.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEEaSzXvEZuAsXptoHO2dZnQ="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEEaSzXvEZuAsXptoHO2dZnQ=")));
                    insnList.add(new InsnNode(89));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEEaSzXvEZuAsXptoHO2dZnQ="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("IFUD6sholIqVci5TzL+xdA=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("lAgah4NuDeD0Ec4pi0idWoZnGm/ud+0szvs+MXD77sE="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("lAgah4NuDeD0Ec4pi0idWoZnGm/ud+0szvs+MXD77sE=")));
                    insnList.add(new InsnNode(89));
                    insnList.add(new InsnNode(3));
                    insnList.add(new InsnNode(3));
                    insnList.add(new InsnNode(3));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("lAgah4NuDeD0Ec4pi0idWoZnGm/ud+0szvs+MXD77sE="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("UIE431Pk7J6zYqWKQee1/A=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEBWORWlpmewa/nnyrGy8jkjqcagshVNAadA2fQQt/irf"))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEBWORWlpmewa/nnyrGy8jkjqcagshVNAadA2fQQt/irf")));
                    insnList.add(new InsnNode(89));
                    insnList.add(new InsnNode(3));
                    insnList.add(new InsnNode(3));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEBWORWlpmewa/nnyrGy8jkjqcagshVNAadA2fQQt/irf"),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("rpAYmufK2/0SWSco3Q7tWw=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEKKgzmZL5R6V2cV+6XJS6Ic="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEKKgzmZL5R6V2cV+6XJS6Ic=")));
                    insnList.add(new InsnNode(89));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(new InsnNode(14));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("MdVFn1h+7U8e2pHXxc1bEKKgzmZL5R6V2cV+6XJS6Ic="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("hj6TDPkGvSnqdkY1njz8ZA=="),
                            false
                        )
                    );
                } else if (internalName.equals(MyCheck.C.dec("jpxbkyI+HcqLRoplyrXojw=="))) {
                    insnList.add(new TypeInsnNode(187, MyCheck.C.dec("jpxbkyI+HcqLRoplyrXojw==")));
                    insnList.add(new InsnNode(89));
                    insnList.add(new InsnNode(9));
                    insnList.add(new InsnNode(9));
                    insnList.add(
                        new MethodInsnNode(
                            183,
                            MyCheck.C.dec("jpxbkyI+HcqLRoplyrXojw=="),
                            MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ=="),
                            MyCheck.C.dec("BKWDZMlek9dagPvbFnwIRA=="),
                            false
                        )
                    );
                } else {
                    insnList.add(new InsnNode(1));
                }
        }
    }

    private boolean isEnumClassName(String className) {
        if (className.equals(MyCheck.C.dec("yaZ/ufZKE2a0YhGjclYUImba960x3fI/jwdH2F9Ns4c="))) {
            return false;
        } else {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            boolean isEnum = false;

            try (InputStream is = cl != null
                    ? cl.getResourceAsStream(className + MyCheck.C.dec("FNfpAc53weZ+kjdVZepsow=="))
                    : ClassLoader.getSystemResourceAsStream(className + MyCheck.C.dec("FNfpAc53weZ+kjdVZepsow=="))) {
                if (is != null) {
                    ClassReader cr = new ClassReader(is);
                    isEnum = (cr.getAccess() & 16384) != 0;
                }
            } catch (Throwable var9) {
            }

            return isEnum;
        }
    }

    public void insnListAddReturn(MethodNode method, InsnList insnList) {
        this.insnListAdd0null(method.desc, insnList);
        Type returnType = Type.getReturnType(method.desc);
        insnList.add(new InsnNode(returnType.getOpcode(172)));
    }

    public void makeMethodCallEmpty(MethodNode method, MethodInsnNode methodInsn) {
        if (!methodInsn.name.equals(MyCheck.C.dec("53vGmCW9xEO6aL8wiSGFuQ==")) && !methodInsn.name.equals(MyCheck.C.dec("d+0KueTGDEG+vXcATNKFnQ=="))) {
            InsnList insnList = new InsnList();
            Type[] args = Type.getArgumentTypes(methodInsn.desc);

            for (int i = args.length - 1; i >= 0; i--) {
                int sort = args[i].getSort();
                if (sort != 7 && sort != 8) {
                    insnList.add(new InsnNode(87));
                } else {
                    insnList.add(new InsnNode(88));
                }
            }

            if (methodInsn.getOpcode() != 184) {
                insnList.add(new InsnNode(87));
            }

            this.insnListAdd0null(methodInsn.desc, insnList);
            method.instructions.insertBefore(methodInsn, insnList);
            method.instructions.remove(methodInsn);
        }
    }

    private void printAllStackTraces(ClassNode classNode, MethodNode method, InsnList insnList) {
    }

    private void concatCaller1NameIntoInsnList(InsnList insnList) {
        insnList.add(new LdcInsnNode(MyCheck.C.dec("J/144qUjeV6JfaVXnC8Kmw==")));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("BelaAr2ZqGu/uzQ6vlXfmg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52"),
                false
            )
        );
        this.pushCaller1NameIntoInsnList(insnList);
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("BelaAr2ZqGu/uzQ6vlXfmg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52"),
                false
            )
        );
    }

    private void pushCaller1NameIntoInsnList(InsnList insnList) {
        this.pushCallerNthNameIntoInsnList(insnList, 1);
    }

    private void pushCallerNthNameIntoInsnList(InsnList insnList, int nth) {
        insnList.add(
            new MethodInsnNode(
                184,
                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("fdzbK6qUW+78VdHx2tK/FQ=="),
                MyCheck.C.dec("Z0dHxxcJPly7JmcSCpnwe2O7mBIjcbDFHs89DfCagV0="),
                false
            )
        );
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("FJA7d/gCp0ZfOUgZr+4kcw=="),
                MyCheck.C.dec("Xl8Hb2GG0zOWXTbAJ+ocy5yhXVuySZbfR7+ae2Qj/ARm2vetMd3yP48HR9hfTbOH"),
                false
            )
        );
        insnList.add(new LdcInsnNode(1 + nth));
        insnList.add(new InsnNode(50));
        insnList.add(new InsnNode(89));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                MyCheck.C.dec("zFL1G6W6qIQGKn7NKhLfaw=="),
                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                false
            )
        );
        insnList.add(new LdcInsnNode(MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("BelaAr2ZqGu/uzQ6vlXfmg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52"),
                false
            )
        );
        insnList.add(new InsnNode(95));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                MyCheck.C.dec("bzjweCiiSU99Fat5oHDpZg=="),
                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                false
            )
        );
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("BelaAr2ZqGu/uzQ6vlXfmg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52"),
                false
            )
        );
        insnList.add(new LdcInsnNode(MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==")));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("BelaAr2ZqGu/uzQ6vlXfmg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52"),
                false
            )
        );
    }

    private void push1stCallerNameWithoutSpecifiedIntoInsnList(MethodNode method, InsnList insnList, List<String> pSpecifiedList) {
        int localStackTraceElementArray = method.maxLocals++;
        int localArrayI = method.maxLocals++;
        int localStackTraceElement = method.maxLocals++;
        int localReturnString = method.maxLocals++;
        LabelNode L_loopStart = new LabelNode();
        LabelNode L_continue = new LabelNode();
        LabelNode L_returnEmpty = new LabelNode();
        LabelNode L_checkNextCaller = new LabelNode();
        insnList.add(
            new MethodInsnNode(
                184,
                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("fdzbK6qUW+78VdHx2tK/FQ=="),
                MyCheck.C.dec("Z0dHxxcJPly7JmcSCpnwe2O7mBIjcbDFHs89DfCagV0="),
                false
            )
        );
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("ASrn1MRjQHamAwR4KBPn/mba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("FJA7d/gCp0ZfOUgZr+4kcw=="),
                MyCheck.C.dec("Xl8Hb2GG0zOWXTbAJ+ocy5yhXVuySZbfR7+ae2Qj/ARm2vetMd3yP48HR9hfTbOH"),
                false
            )
        );
        insnList.add(new VarInsnNode(58, localStackTraceElementArray));
        insnList.add(new InsnNode(5));
        insnList.add(new VarInsnNode(54, localArrayI));
        insnList.add(L_loopStart);
        insnList.add(new VarInsnNode(21, localArrayI));
        insnList.add(new VarInsnNode(25, localStackTraceElementArray));
        insnList.add(new InsnNode(190));
        insnList.add(new JumpInsnNode(162, L_returnEmpty));
        insnList.add(new VarInsnNode(25, localStackTraceElementArray));
        insnList.add(new VarInsnNode(21, localArrayI));
        insnList.add(new InsnNode(50));
        insnList.add(new VarInsnNode(58, localStackTraceElement));
        insnList.add(new VarInsnNode(25, localStackTraceElement));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                MyCheck.C.dec("zFL1G6W6qIQGKn7NKhLfaw=="),
                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                false
            )
        );
        insnList.add(new LdcInsnNode(MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA==")));
        this.insnListAdd_String_concat(insnList);
        insnList.add(new VarInsnNode(25, localStackTraceElement));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("L5xuk6u1NYeptA/2aWf8a86UjwTmh9Wr8xCeInRDyko="),
                MyCheck.C.dec("bzjweCiiSU99Fat5oHDpZg=="),
                MyCheck.C.dec("1Mj2+KwJRsgY574Z1Tt0j93S8ju3AECLfIuFO6rIbVU="),
                false
            )
        );
        this.insnListAdd_String_concat(insnList);
        insnList.add(new LdcInsnNode(MyCheck.C.dec("vH1UMLN13vlFaDZ+i0s15w==")));
        this.insnListAdd_String_concat(insnList);
        insnList.add(new VarInsnNode(58, localReturnString));
        if (pSpecifiedList != null) {
            for (String specified : pSpecifiedList) {
                insnList.add(new VarInsnNode(25, localReturnString));
                insnList.add(new LdcInsnNode(specified));
                this.insnListAdd_String_contains_thenJump(insnList, L_checkNextCaller);
            }
        }

        insnList.add(new VarInsnNode(25, localReturnString));
        insnList.add(new JumpInsnNode(167, L_continue));
        insnList.add(L_checkNextCaller);
        insnList.add(new IincInsnNode(localArrayI, 1));
        insnList.add(new JumpInsnNode(167, L_loopStart));
        insnList.add(L_returnEmpty);
        insnList.add(new LdcInsnNode(MyCheck.C.dec("Ztr3rTHd8j+PB0fYX02zhw==")));
        insnList.add(L_continue);
    }

    public void printPushedString(InsnList insnList) {
        insnList.add(
            new FieldInsnNode(
                178,
                MyCheck.C.dec("ljMyRMt6rWKiAwEz3xo1LWba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("e1UpLiodcWLytJVfeS9sMw=="),
                MyCheck.C.dec("jmKFs2ub1U9IsJJxfLWY0K5MuQpdHrBAlTcmxvoWTKc=")
            )
        );
        insnList.add(new InsnNode(95));
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("hsLVPwIXcgimi/0PHisv0HZ3/wSy6qnYKn/heeXLAKY="),
                MyCheck.C.dec("TDvLqqoIocZwRul01Oj04A=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMIiKLf/BYpYHFdfYVvffY8Q="),
                false
            )
        );
    }

    public void insnListAdd_String_concat(InsnList insnList) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("BelaAr2ZqGu/uzQ6vlXfmg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMMvG69QETN2+RZS7CwTbQAQJwCObbvepWsCU8ObooH52"),
                false
            )
        );
    }

    public void insnListAdd_String_contains_thenJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                false
            )
        );
        insnList.add(new JumpInsnNode(154, label));
    }

    public void insnListAdd_String_contains_elseJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("pr3MVbMaNew5arr33Lo5gA=="),
                MyCheck.C.dec("N/s8WWAOS3kmm0eEofL0yVxlwPvzabPM+p+NnH8p824="),
                false
            )
        );
        insnList.add(new JumpInsnNode(153, label));
    }

    public void insnListAdd_String_startsWith_thenJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                false
            )
        );
        insnList.add(new JumpInsnNode(154, label));
    }

    public void insnListAdd_String_startsWith_elseJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("DalkXkULoPbCs2u2DeEYTg=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                false
            )
        );
        insnList.add(new JumpInsnNode(153, label));
    }

    public void insnListAdd_String_endsWith_thenJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("2zcN2qPT70YeSrhqNkhQqQ=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                false
            )
        );
        insnList.add(new JumpInsnNode(154, label));
    }

    public void insnListAdd_String_endsWith_elseJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("2zcN2qPT70YeSrhqNkhQqQ=="),
                MyCheck.C.dec("RQ+TKxj9T9huqeAYlKNwMAW4irMCgh80PcERM2bBGME="),
                false
            )
        );
        insnList.add(new JumpInsnNode(153, label));
    }

    public void insnListAdd_String_equals_thenJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                false
            )
        );
        insnList.add(new JumpInsnNode(154, label));
    }

    public void insnListAdd_String_equals_elseJump(InsnList insnList, LabelNode label) {
        insnList.add(
            new MethodInsnNode(
                182,
                MyCheck.C.dec("3VKwejrQ+iA5yqyRU7XJ0Gba960x3fI/jwdH2F9Ns4c="),
                MyCheck.C.dec("a/Z+ejwHPep5xcVmXvJQ4A=="),
                MyCheck.C.dec("MA8qM4FhZSz6lg4xdkbPlig27CQk2hTweSA7jLuWD2M="),
                false
            )
        );
        insnList.add(new JumpInsnNode(153, label));
    }

    private void debugSaveClassInspectLogFile(String className, byte[] classfileBuffer1) {
    }

    private static void toMyLogFile(String string) {
    }

    private static String simpleName(String className) {
        int lastIndex = className.lastIndexOf(MyCheck.C.dec("Wo+Eco5sTCT/PJejuDGBNQ=="));
        if (lastIndex == -1) {
            lastIndex = className.lastIndexOf(MyCheck.C.dec("aEeEnSPhCaMmXiK+D4YzwA=="));
        }

        return className.substring(lastIndex + 1);
    }
}
