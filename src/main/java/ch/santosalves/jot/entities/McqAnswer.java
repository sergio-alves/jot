package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mcq_answers database table.
 * 
 */
@Entity
@Table(name="mcq_answers")
@NamedQuery(name="McqAnswer.findAll", query="SELECT m FROM McqAnswer m")
public class McqAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="right_answer", nullable=false)
	private int rightAnswer;

	//bi-directional many-to-one association to Mcq
	@ManyToOne
	@JoinColumn(name="question_id", nullable=false)
	private Mcq mcq;

	//bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name="resource_id", nullable=false)
	private Resource resource;

	public McqAnswer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRightAnswer() {
		return this.rightAnswer;
	}

	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public Mcq getMcq() {
		return this.mcq;
	}

	public void setMcq(Mcq mcq) {
		this.mcq = mcq;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}