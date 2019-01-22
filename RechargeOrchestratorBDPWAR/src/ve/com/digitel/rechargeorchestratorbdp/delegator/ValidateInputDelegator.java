package ve.com.digitel.rechargeorchestratorbdp.delegator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.framework.components.AbstractRule;
import ve.com.digitel.key.Key;
import ve.com.digitel.rechargeorchestratorbdp.bean.BusinessBean;
import ve.com.digitel.rechargeorchestratorbdp.bean.OperationBean;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidAmountParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCreditCardCvcParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCreditCardExpirationDateParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCreditCardHolderIdParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCreditCardHolderNameParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCreditCardNumberParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCreditCardTypeParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidCustomerAccountIdParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidFormOfPayParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidInteractionDateParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidIpParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidOrderNumberParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidParameterInteractionRoleParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidParameterInteractionRoleValueParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidPaymentDateTimeParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidStatusIdParameterException;
import ve.com.digitel.rechargeorchestratorbdp.exception.InvalidTypeOperationParameterException;
import ve.com.digitel.rechargeorchestratorbdp.util.AppProperties;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerAccountInteractionRole;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderItemRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.CustomerOrderRequest;
import ve.com.digitel.rechargeorchestratorbdp.webserviceobject.PartyRole;
import ve.com.digitel.rechargeorchestratorbdp.webservicerequestobjects.*;


public class ValidateInputDelegator extends AbstractRule {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = BSSIntLogger.getBSSIntLogger(ValidateInputDelegator.class);
	
	public ValidateInputDelegator(String name) {
		super(name);
		
	}
	
	public void init() {
		
		logger.info("Inicializando : " + this.getClass().getName());
	}

	@Override
	protected boolean makeDecision(Map<Key, Object> context) {
		System.out.println("makeDecision in ValidateInputDelegator");
		boolean continues = true;
		
		RechargeOrchestratorBDPRequest request=(RechargeOrchestratorBDPRequest) context.get(Key.REQUEST);
		try{
			
			BusinessBean businessBean=loadParametersFromRequest(request);
			//validateBusinessBean(businessBean);
			System.out.println("JKASKAJKBJKBJK "+context.toString());
			context.put(Key.NOT_GSM,businessBean);
			System.out.println("Despues del context "+ context.toString());
			continues=businessBean.isValid();
								
		}catch(Exception e){
			continues=false;
			context.put(Key.ERROR,true);
			context.put(Key.EXCEPTION,e);
			System.out.println(e.getMessage());
		}
		return continues;
	}
	
	
	private void validateBusinessBean(BusinessBean businessBean) throws Exception {
		System.out.println("validateBusinessBean in ValidateInputDelegator");
		businessBean.setValid(validateField(businessBean.getOrderNumber(),"ORDENNUMBER_REGEX"));
		if(businessBean.isValid()){
			System.out.println("validateBusinessBean in ValidateInputDelegator 1");
			 	short key=0;
			 	short valueKey=0;
			 	System.out.println("validateBusinessBean in ValidateInputDelegator 2");
				for(Map.Entry<String,String> element:businessBean.getGlobalParameters().entrySet()){
					System.out.println("validateBusinessBean in ValidateInputDelegator 3");
					if(!validateField(element.getKey(),"INTERACTIONROLE_REGEX")) key++;
					if(!validateField(element.getValue().split(AppProperties.getProperty("SEPARATOR"))[0],"INTERACTIONROLEVALUE_REGEX")) valueKey++;
					if(!validateField(element.getValue().split(AppProperties.getProperty("SEPARATOR"))[1],"INTERACTIONROLEVALUE_REGEX")) valueKey++;
					
				}
				System.out.println("validateBusinessBean in ValidateInputDelegator 4 "+key);
				if(key!=0)
					throw new InvalidParameterInteractionRoleParameterException();
				if(valueKey!=0)
					throw new InvalidParameterInteractionRoleValueParameterException();
				
				if(!validateField(businessBean.getInteractionDate(),"INTERACTIONDATE_REGEX")){
					throw new InvalidInteractionDateParameterException();
				}
				System.out.println("validateBusinessBean in ValidateInputDelegator 5");
				
				for(OperationBean item:businessBean.getOperations()){
					System.out.println("validateBusinessBean in ValidateInputDelegator 6");
					
					if(item.isValid()&&!validateField(item.getFormOfPay(),"FORMOFPAY_REGEX")){
						System.out.println("validateBusinessBean in ValidateInputDelegator 7");
						item.setValid(false);
						item.setException(new InvalidFormOfPayParameterException());
					}
					if(item.isValid()&&!validateField(item.getTypeOperation(),"TYPEOPERATION_REGEX")){
						System.out.println("validateBusinessBean in ValidateInputDelegator 8");
						item.setValid(false);
						item.setException(new InvalidTypeOperationParameterException());
					}
					
					if(item.isValid()){
						System.out.println("validateBusinessBean in ValidateInputDelegator 9");
						if(!validateField(item.getAffiliate(),"CUSTOMERACCOUNTID_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCustomerAccountIdParameterException());
						}
					}
					if(item.isValid()){
						System.out.println("validateBusinessBean in ValidateInputDelegator 10");
						if(!validateField(item.getBeneficiary(),"CUSTOMERACCOUNTID_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCustomerAccountIdParameterException());
						}
					}
					if(item.isValid()){
						if(!validateField(item.getAmount(),"AMOUNT_REGEX")){
							item.setValid(false);
							item.setException(new InvalidAmountParameterException() );
						}
					}
					if(item.isValid()){
						if(!validateField(item.getStatusId(),"STATUSID_REGEX")){
							item.setValid(false);
							item.setException(new InvalidStatusIdParameterException());
						}
					}
					
					if(item.isValid()){
						if(item.getOperationDateTime()!=null||!item.getOperationDateTime().isEmpty()){
							if(!validateField(item.getOperationDateTime(),"PAYMENTDATETIME_REGEX")){
								item.setValid(false);
								item.setException(new InvalidPaymentDateTimeParameterException());
							}
						}	
					}
					
//					if(item.isValid()){
//						if(item.getOperationDateTimeZone()!=null||!item.getOperationDateTimeZone().isEmpty()){
//							if(!validateField(item.getOperationDateTimeZone(),"PAYMENTDATETIMEZONE_REGEX")){
//								item.setValid(false);
//								item.setException(new InvalidPaymentDateTimeZoneParameterException());
//							}
//						}	
//					}
					
					if(item.isValid()){
						if(!validateField(item.getIp(),"IP_REGEX")){
							item.setValid(false);
							item.setException(new InvalidIpParameterException());
						}
					}
					
					if(item.isValid()){
						if(!validateField(item.getCreditCardNumber(),"NUMBERTDC_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCreditCardNumberParameterException());
						}
					}
					
					if(item.isValid()){
						if(!validateField(item.getCreditCardCvc(),"CVCTDC_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCreditCardCvcParameterException());
						}
					}
					
					if(item.isValid()){
						if(!validateField(item.getCreditCardExpirationDate(),"EXPDATETDC_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCreditCardExpirationDateParameterException());
						}
					}
					
					if(item.isValid()){
						if(!validateField(item.getCreditCardType(),"TYPETDC_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCreditCardTypeParameterException());
						}
					}
					
					if(item.isValid()){
						if(!validateField(item.getCreditCardHolderName(),"HOLDERNAMETDC_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCreditCardHolderNameParameterException());
						}
					}
					
					if(item.isValid()){
						if(!validateField(item.getCreditCardHolderId(),"HOLDERIDTDC_REGEX")){
							item.setValid(false);
							item.setException(new InvalidCreditCardHolderIdParameterException());
						}
					}
				}
			
		}else{
			throw new InvalidOrderNumberParameterException();
		}
	}


	private BusinessBean loadParametersFromRequest(RechargeOrchestratorBDPRequest request) throws Exception{
		System.out.println("Aqui");
		BusinessBean bean=new BusinessBean();
		bean.setId(request.getCustomerOrder().getId());
		bean.setOrderNumber(request.getCustomerOrder().getOrderNumber());
		bean.setOperations(new ArrayList<OperationBean>());
		bean.setValid(true);
		bean.setInteractionDate(request.getCustomerOrder().getInteractionDate());
		OperationBean operationBean;
		System.out.println("Aqui2");
		
		CustomerOrderRequest r = new CustomerOrderRequest();
		r = request.getCustomerOrder();
		System.out.println(r.getId());
		System.out.println(r.getInteractionDate());
		System.out.println(r.getPartyInteractionRole().getPartyRole().get(0).getName());
		System.out.println(r.getCustomerOrderItem().get(0).getId());
		
		for(CustomerOrderItemRequest item:request.getCustomerOrder().getCustomerOrderItem()){
			operationBean=new OperationBean();
			operationBean.setValid(true);
			operationBean.setId(item.getId());
			operationBean.setTypeOperation(item.getType());
			operationBean.setFormOfPay(item.getFormOfPay());
			for(CustomerAccountInteractionRole itemAccountInteractionRole:item.getCustomerAccountInteractionRole()){
				if(itemAccountInteractionRole.getInteractionRole().equalsIgnoreCase(AppProperties.getProperty("PARAM_BENEFICIARY"))){
					
					operationBean.setBeneficiary(itemAccountInteractionRole.getCustomerAccount().getId());
				}else if(itemAccountInteractionRole.getInteractionRole().equalsIgnoreCase(AppProperties.getProperty("PARAM_AFFILIATE"))){
					
					operationBean.setAffiliate(itemAccountInteractionRole.getCustomerAccount().getId());
				}
			}
			System.out.println("Aqui3");
			if(item.getPayment()!=null){
				operationBean.setAmount(item.getPayment().getAmount());
//				operationBean.setDescription(item.getOperation().getDescription());
				operationBean.setIp(item.getPayment().getIp());
				operationBean.setStatusId(item.getPayment().getStatusId());
				if(item.getPayment().getPaymentDate()!=null){
					operationBean.setOperationDateTime(item.getPayment().getPaymentDate().getTime());
					//operationBean.setOperationDateTimeZone(item.getPayment().getPaymentDate().getTimeZone());
				}
			}
			System.out.println("Aqui4");
			if(item.getPaymentCard()!=null){
				operationBean.setCreditCardCvc(item.getPaymentCard().getCvc());
				operationBean.setCreditCardExpirationDate(item.getPaymentCard().getExpirationDate());
				if(item.getPaymentCard().getHolder()!=null){
					operationBean.setCreditCardHolderAddress(item.getPaymentCard().getHolder().getAddress());
					operationBean.setCreditCardHolderCity(item.getPaymentCard().getHolder().getCity());
					operationBean.setCreditCardHolderId(item.getPaymentCard().getHolder().getId());
					operationBean.setCreditCardHolderName(item.getPaymentCard().getHolder().getName());
					operationBean.setCreditCardHolderState(item.getPaymentCard().getHolder().getState());
					operationBean.setCreditCardHolderZipCode(item.getPaymentCard().getHolder().getZipCode());
				}
				System.out.println("Aqui4.1");
				operationBean.setCreditCardNumber(item.getPaymentCard().getNumber());
				operationBean.setCreditCardType(item.getPaymentCard().getType());
				operationBean.setCreditCardAlias(item.getPaymentCard().getAlias());
				
			}
			bean.getOperations().add(operationBean);
		}
		System.out.println("Aqui5");
		bean.setGlobalParameters(new HashMap<String,String>());
		System.out.println("Aqui6");
		System.out.println(request.toString());
		System.out.println("1");
		for(PartyRole partyRole: request.getCustomerOrder().getPartyInteractionRole().getPartyRole()){
			bean.getGlobalParameters().put(partyRole.getName(),partyRole.getParty().getPartyId()
					+AppProperties.getProperty("SEPARATOR") 
					+partyRole.getParty().getName());
			System.out.println("dentro del for");
		}
		System.out.println("Aqui7");
		return bean;
	}
	
	private boolean validateField(String field,String regex) throws Exception{
		boolean valid=true;
		regex=AppProperties.getProperty(regex);
		if(field==null||field.isEmpty()){
			valid=!valid;
		}else if(!Pattern.matches(regex,field)){
				valid=!valid;
			}
		return valid;
	}
	
	
	
	
	

}