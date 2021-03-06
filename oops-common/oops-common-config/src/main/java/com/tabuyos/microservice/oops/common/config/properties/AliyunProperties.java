package com.tabuyos.microservice.oops.common.config.properties;

/**
 * <p>Description: </p>
 * <pre>
 *   <b>project: </b><i>tabuyos-microservice</i>
 *   <b>package: </b><i>com.tabuyos.microservice.oops.common.config.properties</i>
 *   <b>class: </b><i>AliyunProperties</i>
 *   comment here.
 * </pre>
 *
 * @author <pre><b>username: </b><i><a href="http://www.tabuyos.com">Tabuyos</a></i></pre>
 * <pre><b>site: </b><i><a href="http://www.tabuyos.com">http://www.tabuyos.com</a></i></pre>
 * <pre><b>email: </b><i>tabuyos@outlook.com</i></pre>
 * <pre><b>description: </b><i>
 *   <pre>
 *     Talk is cheap, show me the code.
 *   </pre>
 * </i></pre>
 * @version 0.1.0
 * @since 0.1.0 - 2/3/21 2:09 PM
 */
public class AliyunProperties {

  private AliyunKeyProperties key = new AliyunKeyProperties();
  private RocketMqProperties rocketMq = new RocketMqProperties();
  private AliyunSmsProperties sms = new AliyunSmsProperties();

  public AliyunKeyProperties getKey() {
    return key;
  }

  public void setKey(AliyunKeyProperties key) {
    this.key = key;
  }

  public RocketMqProperties getRocketMq() {
    return rocketMq;
  }

  public void setRocketMq(RocketMqProperties rocketMq) {
    this.rocketMq = rocketMq;
  }

  public AliyunSmsProperties getSms() {
    return sms;
  }

  public void setSms(AliyunSmsProperties sms) {
    this.sms = sms;
  }

  static class AliyunKeyProperties {
    /**
     * 秘钥id
     */
    private String accessKeyId;

    /**
     * 秘钥
     */
    private String accessKeySecret;

    public String getAccessKeyId() {
      return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
      this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
      return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
      this.accessKeySecret = accessKeySecret;
    }

    @Override
    public String toString() {
      return "AliyunKeyProperties{" +
        "accessKeyId='" + accessKeyId + '\'' +
        ", accessKeySecret='" + accessKeySecret + '\'' +
        '}';
    }
  }

  static class RocketMqProperties {
    private String consumerGroup;
    private String producerGroup;
    private String namesrvAddr;
    /**
     * 生产者是否使用可靠消息, 默认不使用 @MqConsumerStore
     */
    private boolean reliableMessageProducer;
    /**
     * 消费者是否使用可靠消息, 默认不使用 @MqProducerStore
     */
    private boolean reliableMessageConsumer;

    public String getConsumerGroup() {
      return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
      this.consumerGroup = consumerGroup;
    }

    public String getProducerGroup() {
      return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
      this.producerGroup = producerGroup;
    }

    public String getNamesrvAddr() {
      return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
      this.namesrvAddr = namesrvAddr;
    }

    public boolean isReliableMessageProducer() {
      return reliableMessageProducer;
    }

    public void setReliableMessageProducer(boolean reliableMessageProducer) {
      this.reliableMessageProducer = reliableMessageProducer;
    }

    public boolean isReliableMessageConsumer() {
      return reliableMessageConsumer;
    }

    public void setReliableMessageConsumer(boolean reliableMessageConsumer) {
      this.reliableMessageConsumer = reliableMessageConsumer;
    }

    @Override
    public String toString() {
      return "RocketMqProperties{" +
        "consumerGroup='" + consumerGroup + '\'' +
        ", producerGroup='" + producerGroup + '\'' +
        ", namesrvAddr='" + namesrvAddr + '\'' +
        ", reliableMessageProducer=" + reliableMessageProducer +
        ", reliableMessageConsumer=" + reliableMessageConsumer +
        '}';
    }
  }

  static class AliyunSmsProperties {

    /**
     * 阿里云管理控制台中配置的短信签名（状态必须是验证通过）
     */
    private String signName;

    /**
     * 机房信息
     */
    private String regionId;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * 端点名称
     */
    private String endpointName;

    private String product;

    private String domain;

    public String getSignName() {
      return signName;
    }

    public void setSignName(String signName) {
      this.signName = signName;
    }

    public String getRegionId() {
      return regionId;
    }

    public void setRegionId(String regionId) {
      this.regionId = regionId;
    }

    public String getEndpoint() {
      return endpoint;
    }

    public void setEndpoint(String endpoint) {
      this.endpoint = endpoint;
    }

    public String getEndpointName() {
      return endpointName;
    }

    public void setEndpointName(String endpointName) {
      this.endpointName = endpointName;
    }

    public String getProduct() {
      return product;
    }

    public void setProduct(String product) {
      this.product = product;
    }

    public String getDomain() {
      return domain;
    }

    public void setDomain(String domain) {
      this.domain = domain;
    }

    @Override
    public String toString() {
      return "AliyunSmsProperties{" +
        "signName='" + signName + '\'' +
        ", regionId='" + regionId + '\'' +
        ", endpoint='" + endpoint + '\'' +
        ", endpointName='" + endpointName + '\'' +
        ", product='" + product + '\'' +
        ", domain='" + domain + '\'' +
        '}';
    }
  }

  @Override
  public String toString() {
    return "AliyunProperties{" +
      "key=" + key +
      ", rocketMq=" + rocketMq +
      ", sms=" + sms +
      '}';
  }
}
