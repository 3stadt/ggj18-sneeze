speed = 0.25

#happy synth: :tb303

synth_list = [:fm,:tri, :dpulse, :dsaw].ring
synth_counter = 0
define :melody1 do
  play choose(chord(:E3, :minor)), release: 0.3, cutoff: rrand(60, 120) , amp: 0.3
  #play choose(chord(:e3, :minor)), release: 0.1, amp:0.3
end

define :melody2 do
  play choose(chord(:g3, :sus4)), release: 0.3, cutoff: rrand(60, 120) , amp: 0.3
  #play choose(chord(:g3, :sus4)), release: 0.1, amp:0.3
end

define :melody3 do
  play choose(chord(:a3, :sus2)), release: 0.3, cutoff: rrand(60, 120) , amp: 0.3
  
end

live_loop :melodys do
  
  8.times do
    with_synth synth_list[synth_counter] do
      melody1
      sleep speed
    end
  end
  
  8.times do
    with_synth synth_list[synth_counter+1] do
      melody2
      sleep speed
    end
    
  end
  8.times do
    with_synth synth_list[synth_counter+2] do
      melody3
      sleep speed
    end
  end
  synth_counter = synth_counter + 1
end

live_loop :bassdrum do
  sample :bd_ada ,  release: 0.8, amp: 5
  sample :bd_fat , attack: 0.3, release: 0.4, amp: 5
  sleep speed*2
end


live_loop :flibble do
  sample :ambi_choir, rate: 0.3, amp:0.3, attack: 0.5, release:1
  sleep speed*2
  sample :bd_haus, rate: 2
  sleep 1
end